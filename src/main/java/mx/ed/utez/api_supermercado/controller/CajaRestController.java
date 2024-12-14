package mx.ed.utez.api_supermercado.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Queue;

@RestController
@RequestMapping("/caja")
public class CajaRestController {

    private Queue<String> filaClientes = new LinkedList<>();

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarCliente(@RequestBody String cliente) {
        filaClientes.add(cliente);
        return ResponseEntity.ok("Cliente agregado a la fila.");
    }

    @GetMapping("/atender")
    public ResponseEntity<String> atenderCliente() {
        String cliente = filaClientes.poll();
        if (cliente == null) {
            return ResponseEntity.ok("No hay clientes en la fila.");
        }
        return ResponseEntity.ok("Atendiendo al cliente: " + cliente);
    }

    @GetMapping("/obtenerFila")
    public ResponseEntity<Queue<String>> obtenerFila() {
        return ResponseEntity.ok(filaClientes);
    }
}
