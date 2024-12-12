package mx.ed.utez.api_supermercado.model.dao;

import mx.ed.utez.api_supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoDao extends CrudRepository<Producto, Long> {
   Optional<Producto> findByNombre(String nombre);

}
