package com.dbm.main.kafka.consumer;

import com.dbm.main.mongo.model.PaymentCompletedEvent;
import com.dbm.main.mongo.model.PaymentPayload;
import com.dbm.main.mongo.repository.OrderCreatedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dbm.main.mongo.model.OrderCreatedEvent;
import com.dbm.main.utils.DoVariousThings;

import java.util.Calendar;
import java.util.Date;

@Service
public class OrderCreatedConsumer {
    @Autowired
    @Qualifier("paymentcompleted")
    private KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;
    @Autowired
    OrderCreatedRepository orderCreatedRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedConsumer.class);

    @KafkaListener(topics = DoVariousThings.ORDER_CREATED_TOPIC_NAME,
                    groupId = DoVariousThings.GROUP_ID,
                    containerFactory = "orderCreatedKafkaListenerContainerFactory")

    public void consume(OrderCreatedEvent orderCreatedEvent){
        String eventId = orderCreatedEvent.getEventId();
        LOGGER.debug("Order " + eventId + " received");
        LOGGER.debug("Order " + eventId
                + " contains " + orderCreatedEvent.getPayload().getLineItems().size()
                + " line items");
        LOGGER.debug("Order " + eventId
                + " amount: $"
                + orderCreatedEvent.getPayload().getOrderPrice());
        LOGGER.debug("Processing payment for order " + eventId);
        LOGGER.debug("Payment successfully processed for order " + eventId);
        Date createdDate = Calendar.getInstance().getTime();
        PaymentPayload payload = new PaymentPayload(createdDate,orderCreatedEvent.getPayload().getOrderPrice());
        PaymentCompletedEvent event = new PaymentCompletedEvent(eventId,payload,createdDate);
        orderCreatedRepo.save(orderCreatedEvent);
        LOGGER.debug("Order created event persisted for order " + eventId);
        kafkaTemplate.send(DoVariousThings.PAYMENT_COMPLETED_TOPIC_NAME,event);
        kafkaTemplate.flush();
        LOGGER.debug("Payment completed event sent for order " + eventId);
    }
}
