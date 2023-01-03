package com.dbm.main.mongo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.dbm.main.mongo.model.OrderCreatedEvent;

public interface OrderCreatedRepository extends MongoRepository<OrderCreatedEvent, String> {
    @Query("{id:'?0'}")
    OrderCreatedEvent findItemById(String id);
    @Query("{eventId:'?0'}")
    OrderCreatedEvent findItemByEventId(String eventId);
//    @Query(value="{topic:'?0'}", fields="{'topic' : 1, 'messageText' : 1}")
//    List<OrderCreatedEvent> findAll(String topic);
    public long count();

}
