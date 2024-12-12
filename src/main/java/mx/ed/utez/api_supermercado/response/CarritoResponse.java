package mx.ed.utez.api_supermercado.response;

import mx.ed.utez.api_supermercado.model.CarritoProducto;

import java.util.List;

public class CarritoResponse {

    private List<CarritoProducto> carrito;

    public CarritoResponse() {
    }

    public CarritoResponse(List<CarritoProducto> carrito) {
        this.carrito = carrito;
    }

    public List<CarritoProducto> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<CarritoProducto> carrito) {
        this.carrito = carrito;
    }
}
