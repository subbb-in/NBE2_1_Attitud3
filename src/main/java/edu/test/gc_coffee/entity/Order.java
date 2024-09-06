package edu.test.gc_coffee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Order {

    @Id
    private String email;

    private String orderId;

    private String address;

    private String postcode;

    private String orderItems;

    // private enum orderStatus;

}
