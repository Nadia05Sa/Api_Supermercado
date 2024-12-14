package mx.ed.utez.api_supermercado.service;

import jakarta.transaction.Transactional;
import mx.ed.utez.api_supermercado.Custom.CustomStack;
import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.model.dao.ICarritoDao;
import mx.ed.utez.api_supermercado.model.dao.IClienteDao;
import mx.ed.utez.api_supermercado.model.dao.IProductoDao;
import mx.ed.utez.api_supermercado.model.request.EliminarProductoRequest;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements ICarritoService {
    private static final Logger log = LoggerFactory.getLogger(CarritoServiceImpl.class);

    @Autowired
    private ICarritoDao carritoDao;

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IProductoDao productoDao; // Acceso a la base de datos.

    private CustomStack<CarritoProducto> historialEliminados = new CustomStack<>();

    @Override
    @Transactional
    public ResponseEntity<CarritoResponseRest> agregarProducto(CarritoProducto carritoProducto) {
        CarritoResponseRest response = new CarritoResponseRest();

        try {
            // Validar cantidad
            if (carritoProducto.getCantidad() <= 0) {
                response.setMetada("Error", "-1", "La cantidad debe ser mayor a 0");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Verificar existencia del cliente y producto
            Optional<Cliente> clienteOpt = clienteDao.findById(carritoProducto.getCliente().getId());
            Optional<Producto> productoOpt = productoDao.findById(carritoProducto.getProducto().getId());

            if (!clienteOpt.isPresent() || !productoOpt.isPresent()) {
                response.setMetada("Error", "-1", "Cliente o producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Buscar producto existente en el carrito
            CarritoProducto existente = carritoDao
                    .findByClienteAndProducto(clienteOpt.get(), productoOpt.get())
                    .orElse(null);

            if (existente != null) {
                existente.setCantidad(existente.getCantidad() + carritoProducto.getCantidad());
                carritoDao.save(existente);
            } else {
                carritoProducto.setCliente(clienteOpt.get());
                carritoProducto.setProducto(productoOpt.get());
                carritoDao.save(carritoProducto);
            }

            // Respuesta exitosa
            response.getCarritoResponse().setCarrito(carritoDao.findByCliente(clienteOpt.get()));
            response.setMetada("Respuesta OK", "00", "Producto agregado exitosamente al carrito");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error al agregar producto al carrito", e);
            response.setMetada("Error", "-1", "Error inesperado: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @Transactional
    @Override
    public ResponseEntity<CarritoResponseRest> eliminarProducto(EliminarProductoRequest request) {
        CarritoResponseRest response = new CarritoResponseRest();
        try {
            Optional<CarritoProducto> productoOpt = carritoDao.findByClienteIdAndProductoId(
                    request.getClienteId(),
                    request.getProductoId()
            );

            if (productoOpt.isPresent()) {
                CarritoProducto productoExistente = productoOpt.get();

                if (productoExistente.getCantidad() > request.getCantidad()) {
                    productoExistente.setCantidad(productoExistente.getCantidad() - request.getCantidad());
                    carritoDao.save(productoExistente);
                } else {
                    carritoDao.delete(productoExistente);
                }

                historialEliminados.push(productoExistente);

                response.setMetada("Respuesta OK", "00", "Producto eliminado correctamente del carrito");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMetada("Error", "-1", "Producto no encontrado en el carrito");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al eliminar producto del carrito: ", e);
            response.setMetada("Error", "-1", "Error interno al eliminar el producto");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<CarritoResponseRest> deshacerEliminacion() {
        CarritoResponseRest response = new CarritoResponseRest();
        try {
            if (!historialEliminados.isEmpty()) {
                CarritoProducto carritoRestaurado = historialEliminados.pop();

                Optional<CarritoProducto> productoExistenteOpt = carritoDao.findByClienteIdAndProductoId(
                        carritoRestaurado.getCliente().getId(),
                        carritoRestaurado.getProducto().getId()
                );

                if (productoExistenteOpt.isPresent()) {
                    CarritoProducto productoExistente = productoExistenteOpt.get();
                    productoExistente.setCantidad(productoExistente.getCantidad() + carritoRestaurado.getCantidad());
                    carritoDao.save(productoExistente);
                } else {
                    carritoDao.save(carritoRestaurado);
                }

                response.setMetada("Respuesta OK", "00", "Producto restaurado correctamente al carrito");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMetada("Error", "-1", "No hay productos eliminados para deshacer");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al deshacer eliminación de producto: ", e);
            response.setMetada("Error", "-1", "Error al deshacer eliminación");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
