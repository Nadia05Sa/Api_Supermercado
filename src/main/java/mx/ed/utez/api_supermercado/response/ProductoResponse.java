package mx.ed.utez.api_supermercado.response;

import mx.ed.utez.api_supermercado.model.Producto;

import java.util.List;

public class ProductoResponse {
    private List<Producto> productos;

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
