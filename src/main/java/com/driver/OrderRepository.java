//package com.driver;
//
//import io.swagger.models.auth.In;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class OrderRepository {
//    HashMap<String,Order> orderDB=new HashMap<>();
//    HashMap<String,DeliveryPartner> partnerDB=new HashMap<>();
//
//    HashMap<String,List<String>> partnerToOrderDb=new HashMap<>();
//
//    HashSet<String> unassignedOrderDb=new HashSet<>();
//    public String addOrderToDb(String id,Order order){
//        orderDB.put(id,order);
//        unassignedOrderDb.add(id);
//        return "New order added successfully";
//    }
//    public void setUnassignedOrderDb(String orderId){
//        unassignedOrderDb.add(orderId);
//    }
//    public String addPartnerToDb(String partnerId,DeliveryPartner deliveryPartner){
//        partnerDB.put(partnerId,deliveryPartner);
//        return "New delivery partner added successfully";
//    }
//    public Order getOrderByIdFromDb(String orderId){
//        return orderDB.get(orderId);
//    }
//    public DeliveryPartner getPartnerByIdFromDb(String partnerId){
//        return partnerDB.get(partnerId);
//    }
//    public String addOrderPartnerPairToDb(String orderId,String partnerId){
//        if(!partnerToOrderDb.containsKey(partnerId)){
//            partnerToOrderDb.put(partnerId,new ArrayList<>());
//        }
//        partnerToOrderDb.get(partnerId).add(orderId);
//        unassignedOrderDb.remove(orderId);
//        return "New order-partner pair added successfully";
//    }
//    public List<String> getOrdersByPartnerIdFromDb(String partnerId){
//        return partnerToOrderDb.getOrDefault(partnerId,new ArrayList<>());
//    }
//    public List<String> getAllOrdersFromDb(){
//        return new ArrayList<>(orderDB.keySet());
//    }
//    public Integer getCountOfUnassignedOrdersFromDb(){
//        return unassignedOrderDb.size();
//    }
//    public String deletePartnerByIdFromDb(String partnerId){
//        partnerDB.remove(partnerId);
//        partnerToOrderDb.remove(partnerId);
//        return partnerId + " removed successfully";
//    }
//    public String deleteOrderByIdFromDb(String orderId){
//        if(unassignedOrderDb.contains(orderId)){
//            unassignedOrderDb.remove(orderId);
//        }else{
//            for(String partnerId:partnerToOrderDb.keySet()){
//                List<String> orders=partnerToOrderDb.get(partnerId);
//                if(orders.contains(orderId)){
//                    orders.remove(orderId);
//                    DeliveryPartner deliveryPartner=partnerDB.get(partnerId);
//                    deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()-1);
//                    break;
//                }
//            }
//        }
//        orderDB.remove(orderId);
//        return orderId + " removed successfully";
//    }
//}


package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
@Repository
//@Component
public class OrderRepository {
    private HashMap<String, DeliveryPartner> deliveryPartnerDb = new HashMap<>();
    private HashMap<String, Order> orderDb = new HashMap<>();
    private HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb = new HashMap<>();//deliveryPartnerId, list of Orders
    private HashMap<String, String> orderXPartnerDb = new HashMap<>();//orderId =key, partnerId = value

    public void addOrder(Order order){
        String dbkey = order.getId();
        int dTime = order.getDeliveryTime();
        orderDb.put(dbkey, order);
//        if(dbkey.length() != 0 && dTime != 0){
//            orderDb.put(dbkey, order);
//        }
    }
    public Order getOrderById(String orderId){
//       HashMap<String, Order> orderDb = repoObj.getOrderDb();
//        return orderDb.getOrDefault(orderId, null);
        return orderDb.get(orderId);
    }
    public void addPartner(DeliveryPartner partner){
        String dbkey = partner.getId();
        if(dbkey.length() != 0) {
            deliveryPartnerDb.put(dbkey, partner);
        }
    }
    public HashMap<String, String> getOrderXPartnerDb() {
        return orderXPartnerDb;
    }

    public HashMap<String, Order> getOrderDb() {
        return orderDb;
    }
    public HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb() {
        return getOrdersOfDeliveryPartnersDb;
    }
    public HashMap<String, DeliveryPartner> getDeliveryPartnerDb() {
        return deliveryPartnerDb;
    }
    public void setOrdersOfDeliveryPartnersDb(HashMap<String, List<String>> getOrdersOfDeliveryPartnersDb) {
        this.getOrdersOfDeliveryPartnersDb = getOrdersOfDeliveryPartnersDb;
    }
    public void setDeliveryPartnerDb(HashMap<String, DeliveryPartner> deliveryPartnerDb) {
        this.deliveryPartnerDb = deliveryPartnerDb;
    }
    public void setOrderXPartnerDb(HashMap<String, String> orderXPartnerDb) {
        this.orderXPartnerDb = orderXPartnerDb;
    }

    public void setOrderDb(HashMap<String, Order> orderDb) {
        this.orderDb = orderDb;
    }


}



