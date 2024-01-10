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
        orderDb.put(dbkey, order);
    }
    public void addPartner(DeliveryPartner partner){
        String dbkey = partner.getId();
        deliveryPartnerDb.put(dbkey, partner);

    }

    public void setOrdersOfDeliveryPartnersDb(HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb) {
        this.getOrdersOfDeliveryPartnersDb = getOrdersOfDeliveryPartnersDb;
    }

    public HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb() {
        return getOrdersOfDeliveryPartnersDb;
    }

//    public void addOrderPartnerPair(String orderId, String partnerId){
//
//        //This is basically assigning that order to that partnerId
//    }
//    public DeliveryPartner getPartnerById(String partnerId){
//        DeliveryPartner partner = deliveryPartnerDb.get(partnerId);
//        return  partner;
//    }

    public HashMap<String, DeliveryPartner> getDeliveryPartnerDb() {
        return deliveryPartnerDb;
    }
    //    public int getOrderCountByPartnerId(String partnerId){
//        int count = 0;
//        if()
//        return count;
//    }
}
