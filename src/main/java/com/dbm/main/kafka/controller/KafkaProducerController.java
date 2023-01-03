package com.dbm.main.kafka.controller;

import com.dbm.main.mongo.model.LineItem;
import com.dbm.main.mongo.model.OrderCreatedEvent;
import com.dbm.main.mongo.model.OrderPayload;
import com.dbm.main.utils.DoVariousThings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerController.class);

    @Autowired
    @Qualifier("ordercreated")
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @GetMapping("/")
    public ResponseEntity<String> start(){
        OrderCreatedEvent event = DoVariousThings.getOrderCreatedEvent();
        kafkaTemplate.send(DoVariousThings.ORDER_CREATED_TOPIC_NAME,event);
        return ResponseEntity.ok("Order created and e-commerce flow initiated<br><br>" + event.toString());
    }
}
