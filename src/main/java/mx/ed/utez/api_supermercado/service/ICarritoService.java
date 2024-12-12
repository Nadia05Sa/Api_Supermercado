package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import mx.ed.utez.api_supermercado.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICarritoService {
    public ResponseEntity<CarritoResponseRest> agregarProducto(CarritoProducto carritoProducto);
    public ResponseEntity<CarritoResponseRest> listarCarrito(Long id);
    //public ResponseEntity<CarritoResponseRest> eliminarProducto(CarritoProducto carritoProducto);
    //public ResponseEntity<CarritoResponseRest> deshacerEliminacion();
}
