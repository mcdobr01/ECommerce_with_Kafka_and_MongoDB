package com.dbm.main.mongo.model;

import com.dbm.main.utils.DoVariousThings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("inventoryupdated")
public class InventoryUpdatedEvent extends Event{
//	private final String topic = DoVariousThings.INVENTORY_UPDATED_TOPIC_NAME;
	private InventoryPayload payload;
	public InventoryUpdatedEvent(){
		super();
	}

	public InventoryUpdatedEvent(String eventId, InventoryPayload payload, Date eventCreatedDate) {
	    super(eventId,eventCreatedDate);
		this.payload = payload;
	}

	public Date getEventCreatedDate() {
		return eventCreatedDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InventoryPayload getPayload() {
		return payload;
	}

	public void setPayload(InventoryPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		InventoryUpdatedEvent om = (InventoryUpdatedEvent)o;
		return this.eventId.equals(om.eventId)
				&& this.payload.equals(om.payload)
				&& this.eventCreatedDate.equals(om.eventCreatedDate);
	}
}
