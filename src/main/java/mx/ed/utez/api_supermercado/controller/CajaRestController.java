package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.Custom.CustomQueue;
import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.dao.ICarritoDao;
import mx.ed.utez.api_supermercado.model.dao.IClienteDao;
import mx.ed.utez.api_supermercado.service.ICarritoService;
import mx.ed.utez.api_supermercado.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/caja")
public class CajaRestController {

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ICarritoDao carritoDao;

    @Autowired
    private ICarritoService carritoService;

    private final CustomQueue<Cliente> filaClientes = new CustomQueue<>();

    // Método para agregar a un cliente a la fila
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarCliente(@RequestBody Map<String, Object> datos) {
        try {
            // Validar que los datos contengan las claves necesarias
            if (!datos.containsKey("clienteId") || !datos.containsKey("carritoId")) {
                return ResponseEntity.badRequest().body("Faltan clienteId o carritoId en la solicitud");
            }

            // Obtener valores de los datos
            Long clienteId = Long.valueOf(datos.get("clienteId").toString());
            Long carritoId = Long.valueOf(datos.get("carritoId").toString());

            // Buscar cliente y carrito
            Cliente cliente = clienteDao.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            CarritoProducto carrito = carritoDao.findById(carritoId)
                    .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

            // Asociar carrito al cliente
            carrito.setCliente(cliente);
            if (cliente.getCarrito() == null) {
                cliente.setCarrito(new ArrayList<>());
            }
            cliente.getCarrito().add(carrito);

            // Guardar cliente y carrito en la base de datos
            clienteDao.save(cliente);
            carritoDao.save(carrito);

            // Validar que el cliente no esté ya en la fila
            if (!filaClientes.getQueue().contains(cliente)) {
                filaClientes.enqueue(cliente);
                System.out.println("Cliente agregado a la fila: " + cliente.getId());
            } else {
                System.out.println("El cliente ya está en la fila: " + cliente.getId());
            }

            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al agregar cliente: " + e.getMessage());
        }
    }

    // Método para atender a un cliente en la fila
    @GetMapping("/atender")
    public ResponseEntity<?> atenderCliente() {
        try {
            Cliente cliente = filaClientes.dequeue();
            System.out.println("Cliente atendido: " + cliente.getId());
            return ResponseEntity.ok(cliente);
        } catch (IllegalStateException e) {
            System.out.println("No hay clientes en la fila para atender.");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/obtenerFila")
    public ResponseEntity<CustomQueue<Cliente>> obtenerFila() {
        return ResponseEntity.ok(filaClientes);
    }
}
