package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.response.ProductoResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductoService {
    public  ResponseEntity<ProductoResponseRest> guardarProducto(Producto producto);

}
