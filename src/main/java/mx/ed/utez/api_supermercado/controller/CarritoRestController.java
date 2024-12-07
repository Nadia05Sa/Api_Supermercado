package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/carrito")
public class CarritoRestController {

    private List<CarritoProducto> carrito = new ArrayList<>();
    private Stack<CarritoProducto> historialEliminados = new Stack<>();

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarProducto(@RequestBody CarritoProducto producto) {
        carrito.add(producto);
        return ResponseEntity.ok("Producto agregado al carrito.");
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<CarritoProducto>> listarCarrito(@PathVariable Long clienteId) {
        // Filtrar los productos por clienteId
        List<CarritoProducto> productosCliente = carrito.stream()
                .filter(producto -> producto.getCliente().getId().equals(clienteId))
                .toList();
        return ResponseEntity.ok(productosCliente);
    }

    @PostMapping("/eliminar")
    public ResponseEntity<String> eliminarProducto(@RequestBody CarritoProducto producto) {
        if (carrito.remove(producto)) {
            historialEliminados.push(producto);
            return ResponseEntity.ok("Producto eliminado del carrito.");
        }
        return ResponseEntity.badRequest().body("Producto no encontrado en el carrito.");
    }

    @PostMapping("/deshacer")
    public ResponseEntity<String> deshacerEliminacion() {
        if (!historialEliminados.isEmpty()) {
            CarritoProducto ultimoEliminado = historialEliminados.pop();
            carrito.add(ultimoEliminado);
            return ResponseEntity.ok("Última eliminación deshecha.");
        }
        return ResponseEntity.badRequest().body("No hay eliminaciones para deshacer.");
    }
}
