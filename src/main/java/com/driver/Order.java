package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.deliveryTime = Integer.valueOf(deliveryTime);
        this.id = id;
        System.out.println("Hello");
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}