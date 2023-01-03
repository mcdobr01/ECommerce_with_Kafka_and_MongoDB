package com.dbm.main.mongo.model;

import com.dbm.main.utils.DoVariousThings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("ordershipped")
public class OrderShippedEvent extends Event{
//	private final String topic = DoVariousThings.ORDER_SHIPPED_TOPIC_NAME;

	private OrderShipmentPayload payload;

	public OrderShippedEvent(){
		super();
	}

	public OrderShippedEvent(String eventId, OrderShipmentPayload payload, Date eventCreatedDate) {
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

	public OrderShipmentPayload getPayload() {
		return payload;
	}

	public void setPayload(OrderShipmentPayload payload) {
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
		OrderShippedEvent om = (OrderShippedEvent)o;
		return this.eventId.equals(om.eventId)
				&& this.payload.equals(om.payload)
				&& this.eventCreatedDate.equals(om.eventCreatedDate);
	}
}
