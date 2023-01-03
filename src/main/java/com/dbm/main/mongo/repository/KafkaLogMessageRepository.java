package com.dbm.main.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dbm.main.mongo.model.KafkaLogMessage;

public interface KafkaLogMessageRepository extends MongoRepository<KafkaLogMessage, String> {
    
    @Query("{id:'?0'}")
    KafkaLogMessage findItemById(String id);
    
    @Query(value="{source:'?0'}", fields="{'source' : 1, 'severity' : 1, 'logDate' : 1, 'messageId' : 1}")
    List<KafkaLogMessage> findAllBySource(String source);

    @Query(value = "{topic:'logMessage'}", count = true)
    public long count();
}
