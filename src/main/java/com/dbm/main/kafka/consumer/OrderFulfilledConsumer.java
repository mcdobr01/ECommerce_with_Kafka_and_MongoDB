package com.dbm.main.kafka.consumer;

import com.dbm.main.mongo.model.OrderCreatedEvent;
import com.dbm.main.mongo.model.OrderFulfillmentEvent;
import com.dbm.main.mongo.model.OrderPayload;
import com.dbm.main.mongo.repository.OrderCreatedRepository;
import com.dbm.main.mongo.repository.OrderFulfillmentRepository;
import com.dbm.main.utils.DoVariousThings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderFulfilledConsumer {
    @Autowired
    OrderFulfillmentRepository orderFulfillmentRepo;
    @Autowired
    OrderCreatedRepository orderCreatedRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFulfilledConsumer.class);
    Date firstOrderCreatedDate = null;
    Date lastOrderCreatedDate = null;

    @Autowired
    @Qualifier("ordercreated")
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @KafkaListener(topics = DoVariousThings.ORDER_FULFILLED_TOPIC_NAME,
                    groupId = DoVariousThings.GROUP_ID,
            containerFactory = "orderFulfillmentEventKafkaListenerContainerFactory")

    public void consume(OrderFulfillmentEvent orderFulfillmentEvent){
        long ordersCreatedCount = orderCreatedRepo.count();
        if (ordersCreatedCount == 1){
            firstOrderCreatedDate = Calendar.getInstance().getTime();
        }
        String eventId = orderFulfillmentEvent.getEventId();
        LOGGER.debug("Order fulfillment event " + eventId + " received");
        LOGGER.debug("Order " + eventId
                + " fulfilled on " + orderFulfillmentEvent.getPayload().getOrderFulfillmentDate());
        orderFulfillmentRepo.save(orderFulfillmentEvent);
        LOGGER.debug("Order fulfillment event persisted for order " + eventId);
        LOGGER.debug("Updating shipped date and fulfillment status for order " + eventId);
        if (updateOrderStatus(eventId)){
            LOGGER.debug("\n\n\nOrder  " + eventId + " is completed and behind us\n\n\n");
        }else{
            LOGGER.error("Failed to update status for order " + eventId + ", review logs for further information.");
        }
        long ordersFulfilledCount = orderFulfillmentRepo.count();
        if (ordersCreatedCount == ordersFulfilledCount){
            if (ordersCreatedCount < 10000) {
                kafkaTemplate.send(DoVariousThings.ORDER_CREATED_TOPIC_NAME, DoVariousThings.getOrderCreatedEvent());
            }else{
                lastOrderCreatedDate = Calendar.getInstance().getTime();
                LOGGER.info("First order created: " + firstOrderCreatedDate);
                LOGGER.info("Last (10,000th) order created: " + lastOrderCreatedDate);
                long difference_In_Time = lastOrderCreatedDate.getTime() - firstOrderCreatedDate.getTime();
                long difference_In_Seconds = (difference_In_Time / 1000) % 60;
                long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
                long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
                long difference_In_Years = (difference_In_Time / (1000 * 60 * 60 * 24 * 365));
                long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

                LOGGER.info(difference_In_Years
                                + " years, "
                                + difference_In_Days
                                + " days, "
                                + difference_In_Hours
                                + " hours, "
                                + difference_In_Minutes
                                + " minutes, "
                                + difference_In_Seconds
                                + " seconds");

                LOGGER.info("\n\n10,000 orders processed, ending normally\n\n");
                System.exit(0);
            }
        }else{
            LOGGER.error("Count of orders created and fulfilled does not match");
            LOGGER.error("Orders created: " + ordersCreatedCount);
            LOGGER.error("Orders fulfilled: " + ordersFulfilledCount);
            LOGGER.error("Processing ending");
            System.exit(-1);
        }
    }

    private boolean updateOrderStatus(String eventId) {
        try{
            OrderCreatedEvent order = orderCreatedRepo.findItemByEventId(eventId);
            OrderPayload payload = order.getPayload();
            payload.setOrderShippedDate(Calendar.getInstance().getTime());
            payload.setOrderIsFullfilled(true);
            order.setPayload(payload);
            orderCreatedRepo.save(order);
            LOGGER.debug("Order " + eventId
                    + " set to fulfilled, shipped date set to "
                    + payload.getOrderShippedDate().toString());
            return true;
        }catch (Exception e){
            LOGGER.error("Exception(s) occurred updating order status for eventId: " + eventId,e);
            return false;
        }
    }
}
