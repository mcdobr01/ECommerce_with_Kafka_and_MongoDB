package com.dbm.main.mongo.model;

import com.dbm.main.utils.DoVariousThings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("orderfulfilled")
public class OrderFulfillmentEvent extends Event{
//	private final String topic = DoVariousThings.ORDER_FULFILLED_TOPIC_NAME;
	private OrderFulfillmentPayload payload;
	public OrderFulfillmentEvent(){
		super();
	}

	public OrderFulfillmentEvent(String eventId, OrderFulfillmentPayload payload, Date eventCreatedDate) {
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

	public OrderFulfillmentPayload getPayload() {
		return payload;
	}

	public void setPayload(OrderFulfillmentPayload payload) {
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
		OrderFulfillmentEvent om = (OrderFulfillmentEvent)o;
		return this.eventId.equals(om.eventId)
				&& this.payload.equals(om.payload)
				&& this.eventCreatedDate.equals(om.eventCreatedDate);
	}
}
