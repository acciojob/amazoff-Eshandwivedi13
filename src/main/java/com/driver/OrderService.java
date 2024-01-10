package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository repoObj;
    public void addOrder(Order order){
        repoObj.addOrder(order);
    }
    public void addPartner(String partnerId){
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        repoObj.addPartner(partner);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        HashMap<String, List<String>> deliveryPartnersDb = repoObj.getOrdersOfDeliveryPartnersDb();
        if(!deliveryPartnersDb.containsKey(orderId)){
            return;
        }
        List<String> temp = deliveryPartnersDb.get(orderId);
        temp.add(orderId);
        deliveryPartnersDb.put(partnerId, temp);
        repoObj.setOrdersOfDeliveryPartnersDb(deliveryPartnersDb);

//        repoObj.addOrderPartnerPair(orderId, partnerId);
        //This is basically assigning that order to that partnerId
    }
    public Order getOrderById(String orderId){
       HashMap<String, Order> orderDb = repoObj.getOrderDb();
        return orderDb.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        HashMap<String, DeliveryPartner>  partnerDb = repoObj.getDeliveryPartnerDb();
        return  partnerDb.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        HashMap<String, DeliveryPartner> deliveryPartnerDb = repoObj.getDeliveryPartnerDb();
        if(!deliveryPartnerDb.containsKey(partnerId)){
            return 0;
        }
        return deliveryPartnerDb.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        HashMap<String, List<String>> deliveryPartnerDb = repoObj.getOrdersOfDeliveryPartnersDb();
        return deliveryPartnerDb.getOrDefault(partnerId, new ArrayList<>());
    }

    public List<String> getAllOrders(){
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        List<String> orders = new ArrayList<>();
        for(String order : orderDb.keySet()){
            orders.add(order);
        }
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){//can have problems for sure
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        HashMap<String, List<String>> ordersOfDeliveryPartners = repoObj.getOrdersOfDeliveryPartnersDb();
        HashSet<String> assignedOrdersAll = new HashSet<>();
        for(String deliveryPartner : ordersOfDeliveryPartners.keySet()){
            assignedOrdersAll.addAll(ordersOfDeliveryPartners.get(deliveryPartner));
        }
        Integer totalOrders = orderDb.size();
        Integer ordersHavingDeliveryPartners = assignedOrdersAll.size();
        Integer unAssignedOrders = totalOrders - ordersHavingDeliveryPartners;
        return unAssignedOrders;
        //return orderDb.size() - orderXPartnerDb.size();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        //countOfOrders that are left after a particular time of a DeliveryPartner
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        HashMap<String, List<String>>  orderPartnerDb = repoObj.getOrdersOfDeliveryPartnersDb();
        if(!orderPartnerDb.containsKey(partnerId) || orderPartnerDb.get(partnerId) == null || time.length() == 0){
            return 0;
        }
        Integer undelivered = 0;
        // convert time from string to integer
        String hrs = time.substring(0, 2);
        String mints = time.substring(3);
        Integer maxTime = Integer.parseInt(hrs)*60 + Integer.parseInt(mints);//converted everything into mints
        List<String> ordersOfAPartnerList = orderPartnerDb.get(partnerId);
        for(String ord : ordersOfAPartnerList){
            if(orderDb.get(ord).getDeliveryTime() > maxTime){
                undelivered++;
            }
        }
        return undelivered;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
//Return the time when that partnerId will deliver his last delivery order.
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        HashMap<String, List<String>>  orderPartnerDb = repoObj.getOrdersOfDeliveryPartnersDb();
        List<String> ordersOfAPartnerList = orderPartnerDb.get(partnerId);
        int lastDeliveryTime = Integer.MIN_VALUE;
        for(String ord : ordersOfAPartnerList){
            if(orderDb.get(ord).getDeliveryTime() > lastDeliveryTime){
                lastDeliveryTime = orderDb.get(ord).getDeliveryTime();
            }
        }
        int hr =  lastDeliveryTime / 60 ;
        int mint = lastDeliveryTime % 60;
        // now convert this time from int to string
        String hours =  String.valueOf(hr);
        String minutes = String.valueOf(mint);
        return (hours + ":" + minutes);
    }

    public void deletePartnerById(String partnerId){
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        HashMap<String, List<String>>  orderPartnerDb = repoObj.getOrdersOfDeliveryPartnersDb();
        HashMap<String, DeliveryPartner>  deliveryPartnerDb = repoObj.getDeliveryPartnerDb();
        if(deliveryPartnerDb.containsKey(partnerId)){
            // remove from partnersDb
            deliveryPartnerDb.remove(partnerId);
        }
        if(orderPartnerDb.containsKey(partnerId)){
            orderPartnerDb.remove(partnerId);
        }
        repoObj.setOrdersOfDeliveryPartnersDb(orderPartnerDb);
        repoObj.setDeliveryPartnerDb(deliveryPartnerDb);
    }
    public void deleteOrderById(String orderId){
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        HashMap<String, Order> orderDb = repoObj.getOrderDb();
        HashMap<String, List<String>>  orderPartnerDb = repoObj.getOrdersOfDeliveryPartnersDb();
        HashMap<String, DeliveryPartner>  deliveryPartnerDb = repoObj.getDeliveryPartnerDb();
        HashMap<String, String>  orderXPartnerDb = repoObj.getOrderXPartnerDb();
        if(orderDb.containsKey(orderId)){
            orderDb.remove(orderId);
        }
        String partnerId = orderXPartnerDb.get(orderId);
        List<String> orders = orderPartnerDb.get(partnerId);
        for(String order : orders){
            if(order.equals(orderId)){
                orders.remove(orderId);
                break;
            }
        }
        repoObj.setOrdersOfDeliveryPartnersDb(orderPartnerDb);
        repoObj.setDeliveryPartnerDb(deliveryPartnerDb);
        repoObj.setOrderDb(orderDb);

        deliveryPartnerDb.get(partnerId).setNumberOfOrders(orders.size());
//    setNumberOfOrders(deliveryPartnerDb.get(partnerId).size());
    }

}
