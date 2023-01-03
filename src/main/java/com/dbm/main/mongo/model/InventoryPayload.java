package com.dbm.main.mongo.model;

import java.util.Date;

public class InventoryPayload {
    private Date inventoryUpdateCompletedDate;

    public InventoryPayload(){}
    public InventoryPayload(Date inventoryUpdateCompletedDate) {
        this.inventoryUpdateCompletedDate = inventoryUpdateCompletedDate;
    }

    public Date getInventoryUpdateCompleDate() {
        return inventoryUpdateCompletedDate;
    }

    public void setInventoryUpdateCompleDate(Date inventoryUpdateCompletedDate) {
        this.inventoryUpdateCompletedDate = inventoryUpdateCompletedDate;
    }
}
