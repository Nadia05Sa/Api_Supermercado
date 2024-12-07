package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ICarritoService {
    ResponseEntity<String> agregarProducto(CarritoProducto producto);
    ResponseEntity<List<CarritoProducto>> listarCarrito(Long clienteId);
    ResponseEntity<String> eliminarProducto(CarritoProducto producto);
    ResponseEntity<String> deshacerEliminacion();
}