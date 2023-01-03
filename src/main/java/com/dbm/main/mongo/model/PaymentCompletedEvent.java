package com.dbm.main.mongo.model;

import com.dbm.main.utils.DoVariousThings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("paymentcompleted")
public class PaymentCompletedEvent extends Event{
//	private final String topic = DoVariousThings.PAYMENT_COMPLETED_TOPIC_NAME;

	private PaymentPayload payload;

	public PaymentCompletedEvent(){
		super();
	}

	public PaymentCompletedEvent(String eventId, PaymentPayload payload, Date eventCreatedDate) {
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

	public PaymentPayload getPayload() {
		return payload;
	}

	public void setPayload(PaymentPayload payload) {
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
		PaymentCompletedEvent om = (PaymentCompletedEvent)o;
		return this.eventId.equals(om.eventId)
				&& this.payload.equals(om.payload)
				&& this.eventCreatedDate.equals(om.eventCreatedDate);
	}
}
