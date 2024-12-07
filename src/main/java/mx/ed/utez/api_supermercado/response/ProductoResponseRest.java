package mx.ed.utez.api_supermercado.response;

public class ProductoResponseRest extends ProductoResponse {

    private ProductoResponse productoResponse = new ProductoResponseRest();

    public ProductoResponse getProductoResponse() {
        return productoResponse;
    }

    public void setProductoResponse(ProductoResponse productoResponse) {
        this.productoResponse = productoResponse;
    }
}
