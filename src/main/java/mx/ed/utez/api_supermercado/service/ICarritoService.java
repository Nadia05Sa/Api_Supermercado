package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICarritoService {
    ResponseEntity<CarritoResponseRest> agregarProducto(CarritoProducto carritoProducto);
    ResponseEntity<CarritoResponseRest> listarCarrito(Long id);
    ResponseEntity<CarritoResponseRest> eliminarProducto(Long carritoProductoId);
    ResponseEntity<CarritoResponseRest> deshacerEliminacion();
}
