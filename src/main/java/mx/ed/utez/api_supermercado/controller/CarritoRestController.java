package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.request.EliminarProductoRequest;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import mx.ed.utez.api_supermercado.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoRestController {

    @Autowired
    private ICarritoService carritoService;

    @PostMapping("/agregar")
    public ResponseEntity<CarritoResponseRest> agregarProducto(@RequestBody CarritoProducto request) {
        return carritoService.agregarProducto(request);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<CarritoResponseRest> listarCarrito(@PathVariable Long clienteId) {
        return carritoService.listarCarrito(clienteId);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<CarritoResponseRest> eliminarProducto(@RequestBody EliminarProductoRequest request) {
        return carritoService.eliminarProducto(request);
    }


    @PostMapping("/deshacer")
    public ResponseEntity<CarritoResponseRest> deshacerEliminacion() {
        return carritoService.deshacerEliminacion();
    }
}
