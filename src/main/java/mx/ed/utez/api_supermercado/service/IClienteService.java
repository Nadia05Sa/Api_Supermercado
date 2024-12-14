package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.response.ClienteResponseRest;
import org.springframework.http.ResponseEntity;

public interface IClienteService {

    public ResponseEntity<ClienteResponseRest> guardarCliente (Cliente cliente);
}
