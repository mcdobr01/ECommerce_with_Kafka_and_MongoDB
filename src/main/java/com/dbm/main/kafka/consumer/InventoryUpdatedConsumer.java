package com.dbm.main.kafka.consumer;

import com.dbm.main.mongo.model.*;
import com.dbm.main.mongo.repository.InventoryUpdatedRepository;
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
public class InventoryUpdatedConsumer {
    @Autowired
    @Qualifier("ordershipped")
    private KafkaTemplate<String, OrderShippedEvent> kafkaTemplate;
    @Autowired
    InventoryUpdatedRepository inventoryUpdatedRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryUpdatedConsumer.class);

    @KafkaListener(topics = DoVariousThings.INVENTORY_UPDATED_TOPIC_NAME,
                    groupId = DoVariousThings.GROUP_ID,
                    containerFactory = "inventoryUpdatedKafkaListenerContainerFactory")

    public void consume(InventoryUpdatedEvent inventoryUpdatedEvent){
        String eventId = inventoryUpdatedEvent.getEventId();
        LOGGER.debug("Inventory update " + eventId + " received");
        LOGGER.debug("Inventory update " + eventId
                + " completed on " + inventoryUpdatedEvent.getPayload().getInventoryUpdateCompleDate());
        LOGGER.debug("Processing order shipment for inventory update " + eventId);
        LOGGER.debug("Order successfully shipped for inventory update " + eventId);
        Date orderShippedDate = Calendar.getInstance().getTime();
        OrderShipmentPayload payload = new OrderShipmentPayload(orderShippedDate,5);
        OrderShippedEvent event = new OrderShippedEvent(eventId,payload,orderShippedDate);
        inventoryUpdatedRepo.save(inventoryUpdatedEvent);
        LOGGER.debug("Inventory updated event persisted for order " + eventId);
        kafkaTemplate.send(DoVariousThings.ORDER_SHIPPED_TOPIC_NAME,event);
        kafkaTemplate.flush();
        LOGGER.debug("Order shipped event sent for inventory update " + eventId);
    }
}
