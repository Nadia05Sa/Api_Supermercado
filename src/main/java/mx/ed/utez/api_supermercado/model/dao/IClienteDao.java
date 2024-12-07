package mx.ed.utez.api_supermercado.model.dao;

import mx.ed.utez.api_supermercado.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, Long> {
    // Métodos adicionales si son necesarios
}