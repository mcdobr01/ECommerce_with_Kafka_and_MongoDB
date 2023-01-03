package com.dbm.main.mongo.model;

import com.dbm.main.utils.DoVariousThings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("ordercreated")
public class OrderCreatedEvent extends Event{
//	private final String topic = DoVariousThings.ORDER_CREATED_TOPIC_NAME;
	private OrderPayload payload;
	public OrderCreatedEvent(){
		super();
	}

	public OrderCreatedEvent(String eventId, OrderPayload payload, Date eventCreatedDate) {
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
	public OrderPayload getPayload() {
		return payload;
	}
	public void setPayload(OrderPayload payload) {
		this.payload = payload;
	}
	@Override
	public String getEventId(){return this.eventId;}
	@Override
	public void setEventId(String eventId){this.eventId = eventId;}

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
		OrderCreatedEvent om = (OrderCreatedEvent)o;
		return this.eventId.equals(om.eventId)
				&& this.payload.equals(om.payload)
				&& this.eventCreatedDate.equals(om.eventCreatedDate);
	}
}
