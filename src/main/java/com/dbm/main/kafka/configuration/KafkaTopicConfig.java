package com.dbm.main.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import com.dbm.main.mongo.model.*;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaTopicConfig {

    @Value(value="${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value="${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public NewTopic orderCreatedTopic(){
        return TopicBuilder.name("ordercreated")
                .build();
    }

    @Bean
    public NewTopic paymentCompletedTopic(){
        return TopicBuilder.name("paymentcompleted")
                .build();
    }

    @Bean
    public NewTopic inventoryUpdatedTopic(){
        return TopicBuilder.name("inventoryupdated")
                .build();
    }

    @Bean
    public NewTopic orderShippedTopic(){
        return TopicBuilder.name("ordershipped")
                .build();
    }

    @Bean
    public NewTopic orderFulfilledTopic(){
        return TopicBuilder.name("orderfulfilled")
                .build();
    }

    public ConsumerFactory<String, OrderCreatedEvent> orderCreatedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(OrderCreatedEvent.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent>
    orderCreatedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderCreatedConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, PaymentCompletedEvent> paymentCompletedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(PaymentCompletedEvent.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent>
    paymentCompletedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentCompletedConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, InventoryUpdatedEvent> inventoryUpdatedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(InventoryUpdatedEvent.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryUpdatedEvent>
    inventoryUpdatedKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryUpdatedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(inventoryUpdatedConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, OrderShippedEvent> orderShippedConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(OrderShippedEvent.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderShippedEvent>
    orderShippedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderShippedEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderShippedConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, OrderFulfillmentEvent> orderFulfillmentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(OrderFulfillmentEvent.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderFulfillmentEvent>
    orderFulfillmentEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderFulfillmentEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderFulfillmentConsumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, OrderCreatedEvent>
    orderCreatedProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean(name = "ordercreated")
    public KafkaTemplate<String, OrderCreatedEvent>
    orderCreatedKafkaTemplate()
    {
        return new KafkaTemplate<>(
                orderCreatedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, PaymentCompletedEvent>
    paymentCompletedProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean(name = "paymentcompleted")
    public KafkaTemplate<String, PaymentCompletedEvent>
    paymentCompletedKafkaTemplate()
    {
        return new KafkaTemplate<>(
                paymentCompletedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, InventoryUpdatedEvent>
    inventoryUpdatedProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean(name = "inventoryupdated")
    public KafkaTemplate<String, InventoryUpdatedEvent>
    inventoryUpdatedKafkaTemplate()
    {
        return new KafkaTemplate<>(
                inventoryUpdatedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, OrderShippedEvent>
    orderShippedProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean(name = "ordershipped")
    public KafkaTemplate<String, OrderShippedEvent>
    orderShippedKafkaTemplate()
    {
        return new KafkaTemplate<>(
                orderShippedProducerFactory());
    }

    @Bean
    public ProducerFactory<String, OrderFulfillmentEvent>
    orderFulfillmentProducerFactory()
    {
        return new DefaultKafkaProducerFactory<>(getProducerConfig());
    }

    @Bean(name = "orderfulfillment")
    public KafkaTemplate<String, OrderFulfillmentEvent>
    orderFulfillmentKafkaTemplate()
    {
        return new KafkaTemplate<>(
                orderFulfillmentProducerFactory());
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
      Map<String, Object> configs = new HashMap<String, Object>();
      configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      return new KafkaAdmin(configs);
    }

    private Map<String, Object> getProducerConfig(){
        Map<String, Object> config
                = new HashMap<>();

        config.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);

        config.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        config.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return config;
    }
}
