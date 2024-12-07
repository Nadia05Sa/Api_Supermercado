package mx.ed.utez.api_supermercado.response;

import mx.ed.utez.api_supermercado.model.Cliente;

import java.util.List;

public class ClienteResponse {
    private List<Cliente> clientes;

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
