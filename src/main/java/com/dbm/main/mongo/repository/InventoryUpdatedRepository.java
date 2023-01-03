package com.dbm.main.mongo.repository;

import com.dbm.main.mongo.model.InventoryUpdatedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface InventoryUpdatedRepository extends MongoRepository<InventoryUpdatedEvent, String> {
    
    @Query("{id:'?0'}")
    InventoryUpdatedEvent findItemById(String id);
    
    @Query(value="{topic:'?0'}", fields="{'topic' : 1, 'messageText' : 1}")
    List<InventoryUpdatedEvent> findAll(String topic);
    @Query(value = "{topic:{$ne:'logMessage'}}", count = true)
    public long count();

}
