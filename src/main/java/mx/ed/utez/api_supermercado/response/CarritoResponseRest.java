package mx.ed.utez.api_supermercado.response;

public class CarritoResponseRest extends CarritoResponse {
    private CarritoResponse carritoResponse = new CarritoResponse();

    public CarritoResponse getCarritoResponse() {
        return carritoResponse;
    }

    public void setCarritoResponse(CarritoResponse carritoResponse) {
        this.carritoResponse = carritoResponse;
    }
}
