package com.driver;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
@Repository
public class OrderRepository {
    private HashMap<String, DeliveryPartner> deliveryPartnerDb = new HashMap<>();
    private HashMap<String, Order> orderDb = new HashMap<>();
    private HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb = new HashMap<>();//deliveryPartnerId, list of Orders
    private HashMap<String, String> orderXPartnerDb = new HashMap<>();//orderId =key, partnerId = value

    public void setOrderXPartnerDb(HashMap<String, String> orderXPartnerDb) {
        this.orderXPartnerDb = orderXPartnerDb;
    }

    public HashMap<String, String> getOrderXPartnerDb() {
        return orderXPartnerDb;
    }

    public void setDeliveryPartnerDb(HashMap<String, DeliveryPartner> deliveryPartnerDb) {
        this.deliveryPartnerDb = deliveryPartnerDb;
    }

    public void setOrderDb(HashMap<String, Order> orderDb) {
        this.orderDb = orderDb;
    }

    public HashMap<String, Order> getOrderDb() {
        return orderDb;
    }

    public void addOrder(Order order){
        String dbkey = order.getId();
        int dTime = order.getDeliveryTime();
        if(dbkey.length() != 0 && dTime != 0){
            orderDb.put(dbkey, order);
        }
    }
    public void addPartner(DeliveryPartner partner){
        String dbkey = partner.getId();
        if(dbkey.length() != 0) {
            deliveryPartnerDb.put(dbkey, partner);
        }
    }

    public void setOrdersOfDeliveryPartnersDb(HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb) {
        this.getOrdersOfDeliveryPartnersDb = getOrdersOfDeliveryPartnersDb;
    }

    public HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb() {
        return getOrdersOfDeliveryPartnersDb;
    }


    public HashMap<String, DeliveryPartner> getDeliveryPartnerDb() {
        return deliveryPartnerDb;
    }
}
