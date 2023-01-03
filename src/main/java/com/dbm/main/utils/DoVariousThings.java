package com.dbm.main.utils;

import com.dbm.main.mongo.model.LineItem;
import com.dbm.main.mongo.model.OrderCreatedEvent;
import com.dbm.main.mongo.model.OrderPayload;

import java.util.*;

public class DoVariousThings {
    public static final String ORDER_CREATED_TOPIC_NAME = "ordercreated";
    public static final String PAYMENT_COMPLETED_TOPIC_NAME = "paymentcompleted";
    public static final String INVENTORY_UPDATED_TOPIC_NAME = "inventoryupdated";
    public static final String ORDER_SHIPPED_TOPIC_NAME = "ordershipped";
    public static final String ORDER_FULFILLED_TOPIC_NAME = "orderfulfilled";
    public static final String GROUP_ID = "group_id";

    public static String getEventId(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return UUID.randomUUID().toString().replaceAll("_", "");
    }

    public static OrderCreatedEvent getOrderCreatedEvent(){
        double orderPrice = 0;
        LineItem item1 = new LineItem(1,20,"widget", "Super awesome widget that does I know not what",13.99);
        orderPrice += (item1.getQuantity() * item1.getItemPrice());
        LineItem item2 = new LineItem(2,20,"widget holder", "Super awesome widget holder",3.99);
        orderPrice += (item2.getQuantity() * item2.getItemPrice());
        LineItem item3 = new LineItem(3,1,"widget stacker", "Super awesome widget stacker",4.99);
        orderPrice += (item3.getQuantity() * item3.getItemPrice());
        LineItem item4 = new LineItem(4,1,"widget cleaner", "Super awesome widget cleaner",6.99);
        orderPrice += (item4.getQuantity() * item4.getItemPrice());
        ArrayList lineItems = new ArrayList <LineItem>();
        lineItems.add(item1);
        lineItems.add(item2);
        lineItems.add(item3);
        lineItems.add(item4);
        Date orderCreatedDate = Calendar.getInstance().getTime();
        OrderPayload payload = new OrderPayload(orderCreatedDate,lineItems,orderPrice,false);
        return new OrderCreatedEvent(DoVariousThings.getEventId(),payload,orderCreatedDate);
    }
}
