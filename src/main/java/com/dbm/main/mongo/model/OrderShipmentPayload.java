package com.dbm.main.mongo.model;

import java.util.Date;

public class OrderShipmentPayload {
    private Date orderShipmentDate;
    private int shippingDays;

    public OrderShipmentPayload(){}

    public OrderShipmentPayload(Date orderShipmentDate, int shippingDays) {
        this.orderShipmentDate = orderShipmentDate;
        this.shippingDays = shippingDays;
    }

    public Date getOrderShipmentDate() {
        return orderShipmentDate;
    }

    public void setOrderShipmentDate(Date orderShipmentDate) {
        this.orderShipmentDate = orderShipmentDate;
    }

    public double getShippingDays() {
        return shippingDays;
    }

    public void setShippingDays(int shippingDays) {
        this.shippingDays = shippingDays;
    }
}
