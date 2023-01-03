package com.dbm.main.mongo.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Collection;

public class OrderPayload {
    private Date orderCreatedDate;
    private Date orderShippedDate;
    private Collection<LineItem> lineItems;
    private double orderPrice;
    private boolean orderIsFullfilled;

    public OrderPayload(){}

    public OrderPayload(Date orderCreatedDate, Collection<LineItem> lineItems, double orderPrice, boolean orderIsFullfilled) {
        this.orderCreatedDate = orderCreatedDate;
        this.lineItems = lineItems;
        this.orderPrice = orderPrice;
        this.orderIsFullfilled = orderIsFullfilled;
    }

    public Date getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(Date orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public Date getOrderShippedDate() {
        return orderShippedDate;
    }

    public void setOrderShippedDate(Date orderShippedDate) {
        this.orderShippedDate = orderShippedDate;
    }

    public Collection<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Collection<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isOrderIsFullfilled() {
        return orderIsFullfilled;
    }

    public void setOrderIsFullfilled(boolean orderIsFullfilled) {
        this.orderIsFullfilled = orderIsFullfilled;
    }

    @Override
    public String toString(){
        return this.toJsonString();
    }

    public String toJsonString(){
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(this);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
