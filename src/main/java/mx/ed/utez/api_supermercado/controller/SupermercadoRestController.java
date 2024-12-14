package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.dao.ICarritoDao;
import mx.ed.utez.api_supermercado.model.dao.IClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supermercado")
public class SupermercadoRestController {

    @Autowired
    private  IClienteDao clienteDao;

    @Autowired
    private ICarritoDao carritoDao;


    @PostMapping("/comprar/{clienteId}")
    public ResponseEntity<?> procesarCompra(@PathVariable Long clienteId) {
        try {
            // Buscar al cliente por su ID
            Cliente cliente = clienteDao.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            // Obtener los productos en el carrito del cliente
            List<CarritoProducto> carritoProductos = carritoDao.findByCliente(cliente);

            if (carritoProductos.isEmpty()) {
                return ResponseEntity.badRequest().body("El carrito está vacío.");
            }

            // Calcular el total de la compra
            double total = carritoProductos.stream()
                    .mapToDouble(carritoProducto -> carritoProducto.getCantidad() * carritoProducto.getProducto().getPrecio())
                    .sum();

            // Realizar el proceso de compra (por ejemplo, registrar una orden, actualizar inventarios, etc.)
            // Simularemos el vaciado del carrito tras la compra
            carritoProductos.forEach(carritoProducto -> carritoDao.deleteById(carritoProducto.getId()));

            return ResponseEntity.ok("Compra procesada exitosamente. Total: $" + total);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la compra: " + e.getMessage());
        }
    }
}
