package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteRestController {

    @PostMapping("/agregarCliente")
    public ResponseEntity<String> agregarCliente(@RequestBody Cliente cliente) {
        // Lógica para guardar el cliente (persistir en la base de datos)
        return ResponseEntity.ok("Cliente registrado correctamente.");
    }
}
