package mx.ed.utez.api_supermercado.response;

import mx.ed.utez.api_supermercado.response.CarritoResponse;
import mx.ed.utez.api_supermercado.response.ResponseRest;

public class CarritoResponseRest extends ResponseRest {

    private CarritoResponse carritoResponse = new CarritoResponse();
    public CarritoResponse getCarritoResponse() {
        return carritoResponse;
    }
    public void setCarritoResponse(CarritoResponse carritoResponse) {
        this.carritoResponse = carritoResponse;
    }
}