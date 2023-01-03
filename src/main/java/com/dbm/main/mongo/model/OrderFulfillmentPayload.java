package com.dbm.main.mongo.model;

import java.util.Date;

public class OrderFulfillmentPayload {
    private Date orderFulfillmentDate;

    public OrderFulfillmentPayload(){}

    public OrderFulfillmentPayload(Date orderFulfillmentDate) {
        this.orderFulfillmentDate = orderFulfillmentDate;
    }

    public Date getOrderFulfillmentDate() {
        return orderFulfillmentDate;
    }
    public void setOrderFulfillmentDate(Date orderFulfillmentDate) {
        this.orderFulfillmentDate = orderFulfillmentDate;
    }
}
