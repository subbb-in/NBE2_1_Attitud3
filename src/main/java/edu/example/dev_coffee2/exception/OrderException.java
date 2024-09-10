package edu.example.dev_coffee2.exception;

public enum OrderException {
    NOT_FOUND_ORDER("ORDER NOT_FOUND", 404),
    NOT_FOUND_ORDERITEM("OrderItem NOT_FOUND", 404),
    PRODUCT_NOT_FOUND("Product Not Found for ORDER", 404),
    FAIL_ADD("ORDER Add Fail", 400),

    FAIL_MODIFY("ORDER Modify Fail", 400),
    FAIL_REMOVE("ORDER Remove Fail", 400),

    NOT_FETCHED("ORDER Not Fetched", 400),
    NOT_MATCHED_ORDERITEM("ORDERItem Not Matched", 400),
    NOT_MATCHED_EMAIL("Email Not Matched", 400),

    NOT_MATCHED_PRICE("Price Not Matched", 400);

    private OrderTaskException cartTaskException;

    OrderException(String message, int code) {
        cartTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get(){
        return cartTaskException;
    }
}
