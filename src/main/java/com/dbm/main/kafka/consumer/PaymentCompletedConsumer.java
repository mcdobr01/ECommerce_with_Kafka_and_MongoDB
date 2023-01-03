package com.dbm.main.kafka.consumer;

import com.dbm.main.mongo.model.*;
import com.dbm.main.mongo.repository.PaymentCompletedRepository;
import com.dbm.main.utils.DoVariousThings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PaymentCompletedConsumer {
    @Autowired
    private KafkaTemplate<String, InventoryUpdatedEvent> kafkaTemplate;
    @Autowired
    PaymentCompletedRepository paymentCompletedRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCompletedConsumer.class);

    @KafkaListener(topics = DoVariousThings.PAYMENT_COMPLETED_TOPIC_NAME ,
                    groupId = DoVariousThings.GROUP_ID,
                    containerFactory = "paymentCompletedKafkaListenerContainerFactory")

    public void consume(PaymentCompletedEvent paymentCompletedEvent){
        String eventId = paymentCompletedEvent.getEventId();
        LOGGER.debug("Payment " + eventId + " received");
        LOGGER.debug("Payment " + eventId
                + " completed on " + paymentCompletedEvent.getPayload().getPaymentCompletedDate());
        LOGGER.debug("Payment " + eventId
                        + " completed for $" + paymentCompletedEvent.getPayload().getPaymentAmount());
        LOGGER.debug("Processing inventory update for payment " + eventId);
        LOGGER.debug("Inventory successfully updated for payment " + eventId);
        Date createdDate = Calendar.getInstance().getTime();
        InventoryPayload payload = new InventoryPayload(createdDate);
        InventoryUpdatedEvent event = new InventoryUpdatedEvent(eventId,payload,createdDate);
        paymentCompletedRepo.save(paymentCompletedEvent);
        LOGGER.debug("Payment completed event persisted for payment " + eventId);
        kafkaTemplate.send(DoVariousThings.INVENTORY_UPDATED_TOPIC_NAME,event);
        kafkaTemplate.flush();
        LOGGER.debug("Inventory updated event sent for payment " + eventId);
    }
}
