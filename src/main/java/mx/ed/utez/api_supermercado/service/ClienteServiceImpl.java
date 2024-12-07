package mx.ed.utez.api_supermercado.service;

import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.dao.IClienteDao;
import mx.ed.utez.api_supermercado.response.ClienteResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClienteServiceImpl implements IClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional
    public ResponseEntity<ClienteResponseRest> guardarCliente(Cliente cliente) {
        log.info("Inicio metodo crear Cliente");

        ClienteResponseRest response = new ClienteResponseRest();
        List<Cliente> list = new ArrayList<>();

        try {
            // Use the injected instance of clienteDao to call save()
            Cliente ClienteGuardado = clienteDao.save(cliente);

            if (ClienteGuardado != null) {
                list.add(ClienteGuardado);
                response.getClienteResponse().setClientes(list);
            } else {
                log.error("Error en grabar cliente");
                response.setMetada("Respuesta nok", "-1", "Cliente no guardado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error("Error en guardar el Cliente");
            response.setMetada("Respuesta nok", "-1", "Error en guardar el Cliente");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setMetada("Respuesta ok", "00", "Cliente creado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
