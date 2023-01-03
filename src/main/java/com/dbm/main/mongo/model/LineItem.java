package com.dbm.main.mongo.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LineItem {
    private long itemInventoryId;
    private int quantity;
    private String itemName;
    private String itemDescription;
    private double itemPrice;

    public LineItem(){
        super();
    }

    public LineItem(long itemInventoryId, int quantity, String itemName, String itemDescription, double itemPrice) {
        this.itemInventoryId = itemInventoryId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
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


    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemInventoryId() {
        return itemInventoryId;
    }

    public void setItemInventoryId(long itemInventoryId) {
        this.itemInventoryId = itemInventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
