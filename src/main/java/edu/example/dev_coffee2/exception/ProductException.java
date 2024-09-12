package edu.example.dev_coffee2.exception;

public enum ProductException {
    NOT_FOUND("Product NOT_FOUND", 404),
    NOT_REGISTERED("Product Not Registered", 400),
    NOT_MODIFIED("Product Not Modified", 400),
    NOT_REMOVED("Product Not Removed", 400),
    NOT_FETCHED("Product Not Fetched", 400);

    private ProductTaskException productTaskException;

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }

    public ProductTaskException get(){
        return productTaskException;
    }
}
