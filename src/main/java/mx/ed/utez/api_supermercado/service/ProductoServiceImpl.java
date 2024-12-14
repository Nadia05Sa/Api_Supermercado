package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.model.dao.IProductoDao;
import mx.ed.utez.api_supermercado.response.ProductoResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional
    public ResponseEntity<ProductoResponseRest> guardarProducto(Producto producto) {
        log.info("Inicio metodo crear Producto");

        ProductoResponseRest response = new ProductoResponseRest();
        List<Producto> list = new ArrayList<>();

        try {
            // Guarda el producto usando el método save() del DAO
            Producto productoGuardado = productoDao.save(producto);

            if (productoGuardado != null) {
                list.add(productoGuardado);
                response.getProductoResponse().setProductos(list);
            } else {
                log.error("Error en grabar producto");
                response.setMetada("Respuesta nok", "-1", "Producto no guardado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error("Error en guardar el Producto", e);
            response.setMetada("Respuesta nok", "-1", "Error en guardar el Producto");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetada("Respuesta ok", "00", "Producto creado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
