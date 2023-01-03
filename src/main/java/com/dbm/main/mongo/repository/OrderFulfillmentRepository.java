package com.dbm.main.mongo.repository;

import com.dbm.main.mongo.model.OrderFulfillmentEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderFulfillmentRepository extends MongoRepository<OrderFulfillmentEvent, String> {

    @Query("{id:'?0'}")
    OrderFulfillmentEvent findItemById(String id);
    @Query("{eventId:'?0'}")
    OrderFulfillmentEvent findItemByEventId(String eventId);
    
//    @Query(value="{topic:'?0'}", fields="{'topic' : 1, 'messageText' : 1}")
//    List<OrderFulfillmentEvent> findAll(String topic);
    public long count();

}
