package mx.ed.utez.api_supermercado.response;

import mx.ed.utez.api_supermercado.model.CarritoProducto;
import mx.ed.utez.api_supermercado.response.CarritoResponse;
import mx.ed.utez.api_supermercado.response.ResponseRest;

import java.util.List;

public class CarritoResponseRest extends ResponseRest {

    private CarritoResponse carritoResponse = new CarritoResponse();
    public CarritoResponse getCarritoResponse() {
        return carritoResponse;
    }
    public void setCarritoResponse(CarritoResponse carritoResponse) {
        this.carritoResponse = carritoResponse;
    }

    private List<CarritoProducto> productos;
    private Double total;


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}