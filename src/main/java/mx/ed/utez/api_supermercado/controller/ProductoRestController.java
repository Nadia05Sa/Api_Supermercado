package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoRestController {

    @PostMapping("/agregarProducto")
    public ResponseEntity<String> agregarProducto(@RequestBody Producto producto) {
        // Lógica para guardar el producto (persistir en la base de datos)
        return ResponseEntity.ok("Producto registrado correctamente.");
    }
}
