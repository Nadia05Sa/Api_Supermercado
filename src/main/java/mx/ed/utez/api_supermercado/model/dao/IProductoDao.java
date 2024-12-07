package mx.ed.utez.api_supermercado.model.dao;

import mx.ed.utez.api_supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Long> {
    // Métodos personalizados

    // Buscar productos por nombre (coincidencia exacta)
    Producto findByNombre(String nombre);

}
