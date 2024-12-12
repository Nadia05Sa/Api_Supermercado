package mx.ed.utez.api_supermercado.model.dao;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.Cliente;
import mx.ed.utez.api_supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarritoDao extends CrudRepository<CarritoProducto, Long> {

    List<CarritoProducto> findByCliente(Cliente cliente);

    @Query("SELECT cp FROM CarritoProducto cp WHERE cp.cliente.id = :cliente_id")
    List<CarritoProducto> findProductosByClienteId(@Param("cliente_id") Long cliente_id);

    Optional<CarritoProducto> findByClienteAndProducto(Cliente cliente, Producto producto);

}
