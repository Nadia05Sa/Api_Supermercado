package mx.ed.utez.api_supermercado.controller;

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

    private final Queue<Cliente> filaClientes = new LinkedList<>();


}
