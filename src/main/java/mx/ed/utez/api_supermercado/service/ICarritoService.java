package mx.ed.utez.api_supermercado.service;

import jakarta.transaction.Transactional;
import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.request.EliminarProductoRequest;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICarritoService {
    ResponseEntity<CarritoResponseRest> agregarProducto(CarritoProducto carritoProducto);
    ResponseEntity<CarritoResponseRest> listarCarrito(Long id);
    ResponseEntity<CarritoResponseRest> eliminarProducto(EliminarProductoRequest carritoProductoId);
    ResponseEntity<CarritoResponseRest> deshacerEliminacion();
}
