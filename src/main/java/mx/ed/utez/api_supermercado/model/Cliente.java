package mx.ed.utez.api_supermercado.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CarritoProducto> carrito;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<CarritoProducto> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<CarritoProducto> carrito) {
        this.carrito = carrito;
    }

    @Override
    public boolean equals(Object o) {
        // Verifica si el objeto actual es el mismo que el objeto pasado como parámetro
        if (this == o) return true;
        // Verifica si el objeto pasado es nulo o si no pertenece a la misma clase
        if (o == null || getClass() != o.getClass()) return false;
        // Realiza un casting del objeto para compararlo como un objeto de tipo Cliente
        Cliente cliente = (Cliente) o;
        // Compara los valores de los atributos 'id' de ambos objetos usando Objects.equals
        // Esto permite manejar posibles valores nulos del atributo 'id' sin errores de NullPointerException
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        // Calcula un código hash basado únicamente en el atributo 'id' del cliente
        // Esto asegura que dos objetos con el mismo 'id' generen el mismo valor hash
        return Objects.hash(id);
    }

}
