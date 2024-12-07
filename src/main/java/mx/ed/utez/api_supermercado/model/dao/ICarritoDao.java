package mx.ed.utez.api_supermercado.model.dao;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.model.CarritoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarritoDao extends JpaRepository<CarritoProducto, Long> {
    // Si necesitas buscar carritos por cliente, puedes agregar métodos personalizados.

    // Ejemplo: Buscar un carrito por cliente
    CarritoProducto findByClienteId(Long clienteId);
}
