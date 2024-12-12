package mx.ed.utez.api_supermercado.service;

import jakarta.transaction.Transactional;
import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.model.dao.ICarritoDao;
import mx.ed.utez.api_supermercado.model.dao.IClienteDao;
import mx.ed.utez.api_supermercado.model.dao.IProductoDao;
import mx.ed.utez.api_supermercado.response.CarritoResponse;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class CarritoServiceImpl implements ICarritoService {
private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);
    @Autowired
    private ICarritoDao carritoDao;
    @Autowired
    private IClienteDao clienteDao;
    @Autowired
    private IProductoDao productoDao;// Acceso a la base de datos.

    private Stack<CarritoProducto> historialEliminados = new Stack<>();

    @Override
    @Transactional
    public ResponseEntity<CarritoResponseRest> agregarProducto(CarritoProducto carritoProducto) {
        CarritoResponseRest response = new CarritoResponseRest();
        List<CarritoProducto> listaProductos = new ArrayList<>();

        try {
            // Validaciones iniciales
            if (carritoProducto.getCliente() == null || carritoProducto.getProducto() == null) {
                response.setMetada("Error", "-1", "Cliente o Producto no pueden ser nulos");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Verificar que el producto existe
            Optional<Producto> productoOpt = productoDao.findById(carritoProducto.getProducto().getId());
            if (!productoOpt.isPresent()) {
                response.setMetada("Error", "-1", "Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Verificar si el producto ya está en el carrito
            Optional<CarritoProducto> existente = carritoDao.findByClienteAndProducto(carritoProducto.getCliente(), carritoProducto.getProducto());
            if (existente.isPresent()) {
                log.debug("Producto ya existente en el carrito. Incrementando cantidad.");
                existente.get().setCantidad(existente.get().getCantidad() + carritoProducto.getCantidad());
                carritoDao.save(existente.get());
            } else {
                log.debug("Producto no está en el carrito. Agregándolo.");
                carritoDao.save(carritoProducto);
            }

            // Obtener la lista de productos actualizada
            listaProductos = carritoDao.findByCliente(carritoProducto.getCliente());
            response.getCarritoResponse().setCarrito(listaProductos);
            response.setMetada("Respuesta OK", "00", "Producto agregado exitosamente al carrito");

        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos: ", e);
            response.setMetada("Error", "-1", "Violación de integridad de datos: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("Error inesperado: ", e);
            response.setMetada("Error", "-1", "Error inesperado: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public ResponseEntity<CarritoResponseRest> listarCarrito(Long id) {
        log.info("Consultando carrito por ID de cliente: " + id);
        CarritoResponseRest response = new CarritoResponseRest();
        List<CarritoProducto> productosEnCarrito = new ArrayList<>();

        try {
            Optional<Cliente> cliente = clienteDao.findById(id);

            if (cliente.isPresent()) {
                productosEnCarrito = carritoDao.findByCliente(cliente.get());

                if (!productosEnCarrito.isEmpty()) {
                    response.getCarritoResponse().setCarrito(productosEnCarrito);
                    response.setMetada("Respuesta OK", "00", "Productos encontrados en el carrito");
                } else {
                    log.info("El carrito del cliente no contiene productos");
                    response.setMetada("Respuesta vacía", "-1", "El carrito está vacío");
                    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
                }
            } else {
                log.info("Cliente no encontrado");
                response.setMetada("Respuesta no encontrada", "-1", "Cliente no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al consultar el carrito", e);
            response.setMetada("Error", "-1", "Error interno al consultar el carrito");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<String> eliminarProducto(CarritoProducto producto) {
//        try {
//            carritoDao.delete(producto);
//            historialEliminados.push(producto);
//            return ResponseEntity.ok("Producto eliminado del carrito.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error al eliminar producto.");
//        }
//    }


//    @Override
//    public ResponseEntity<String> deshacerEliminacion() {
//        if (!historialEliminados.isEmpty()) {
//            CarritoProducto ultimoEliminado = historialEliminados.pop();
//            carritoDao.save(ultimoEliminado);
//            return ResponseEntity.ok("Última eliminación deshecha.");
//        }
//        return ResponseEntity.badRequest().body("No hay eliminaciones para deshacer.");
//    }
}
