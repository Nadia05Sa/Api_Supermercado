    package mx.ed.utez.api_supermercado.controller;

    import mx.ed.utez.api_supermercado.model.Cliente;
    import mx.ed.utez.api_supermercado.service.IClienteService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/cliente")
    public class ClienteRestController {

        @Autowired
        private IClienteService clienteService;

        @PostMapping("/agregarCliente")
        public ResponseEntity<String> agregarCliente(@RequestBody Cliente cliente) {
            clienteService.guardarCliente(cliente);
            return ResponseEntity.ok("Cliente registrado correctamente.");
        }
    }
