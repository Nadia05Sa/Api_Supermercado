package mx.ed.utez.api_supermercado.controller;

import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.Producto;
import mx.ed.utez.api_supermercado.model.dao.IProductoDao;
import mx.ed.utez.api_supermercado.service.IClienteService;
import mx.ed.utez.api_supermercado.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoRestController {

    @Autowired
    private IProductoService productoService;


    @PostMapping("/agregarProducto")
    public ResponseEntity<String> agregarProducto(@RequestBody Producto producto) {
        productoService.guardarProducto(producto);
        return ResponseEntity.ok("Producto registrado correctamente.");
    }
}


