package com.dbm.main.kafka.consumer;

import com.dbm.main.mongo.model.OrderFulfillmentEvent;
import com.dbm.main.mongo.model.OrderFulfillmentPayload;
import com.dbm.main.mongo.model.OrderShippedEvent;
import com.dbm.main.mongo.repository.OrderShippedRepository;
import com.dbm.main.utils.DoVariousThings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class OrderShippedConsumer {
    @Autowired
    @Qualifier("orderfulfillment")
    private KafkaTemplate<String, OrderFulfillmentEvent> kafkaTemplate;
    @Autowired
    OrderShippedRepository orderShippedRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderShippedConsumer.class);

    @KafkaListener(topics = DoVariousThings.ORDER_SHIPPED_TOPIC_NAME,
                    groupId = DoVariousThings.GROUP_ID,
                    containerFactory = "orderShippedEventKafkaListenerContainerFactory")

    public void consume(OrderShippedEvent orderShippedEvent){
        String eventId = orderShippedEvent.getEventId();
        LOGGER.debug("Order shipment event " + eventId + " received");
        LOGGER.debug("Order shipment event " + eventId
                + " completed on " + orderShippedEvent.getPayload().getOrderShipmentDate());
        LOGGER.debug("Order shipment event " + eventId
                + " should arrive " + orderShippedEvent.getPayload().getShippingDays()
                + " days later");
        LOGGER.debug("Order successfully shipped for inventory update " + eventId);
        orderShippedRepo.save(orderShippedEvent);
        LOGGER.debug("Order shipment event persisted for order " + eventId);
        Date fulfilledDate = Calendar.getInstance().getTime();
        OrderFulfillmentPayload payload = new OrderFulfillmentPayload(fulfilledDate);
        OrderFulfillmentEvent event = new OrderFulfillmentEvent(eventId,payload,fulfilledDate);
        kafkaTemplate.send(DoVariousThings.ORDER_FULFILLED_TOPIC_NAME,event);
        kafkaTemplate.flush();
        LOGGER.debug("Order fulfilled event sent for order " + eventId);
    }
}
