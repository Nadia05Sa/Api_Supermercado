package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.response.CarritoResponseRest;
import mx.ed.utez.api_supermercado.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/carrito")
public class CarritoRestController {


    @Autowired
    private ICarritoService carritoService;


    @PostMapping("/agregar")
    public ResponseEntity<CarritoResponseRest> agregarProducto(@RequestBody CarritoProducto request) {
        ResponseEntity<CarritoResponseRest> response = carritoService.agregarProducto(request);
        return response;
    }


    @GetMapping("/{clienteId}")

    public ResponseEntity<CarritoResponseRest> listarCarrito(@PathVariable Long clienteId){
        ResponseEntity<CarritoResponseRest> response = carritoService.listarCarrito(clienteId);
        return response;
    }

//    @PostMapping("/eliminar")
//    public ResponseEntity<CarritoResponseRest> eliminarProducto(@RequestBody CarritoProducto request) {
//        return carritoService.eliminarProducto(request);
//    }

//    @PostMapping("/deshacer")
//    public ResponseEntity<CarritoResponseRest> deshacerEliminacion() {
//        return carritoService.deshacerEliminacion();
//    }

}
