# ECommerce_with_Kafka_and_MongoDB
 
This was created as a learning curve for both Kafka messaging and MongoDB.

The idea is meant to illustrate a barebones ecommerce example flow:
1. An order is created and submitted to the ordercreated Kafka topic
2. The ordercreated consumer picks this up, persists the order created event in Mongo and drops an event to the paymentcompleted topic
3. The paymentcompleted consumer picks this up, persists the paymentcompleted event to Mongo and drops an event to the inventoryupdated topic
4. The inventoryupdated consumer picks this up, persists the inventoryupdated event to Mongo and drops an event to the ordershipped topic
5. The ordershipped consumer picks this up, pewrsists the ordershipped event to Mongo and drops an event to the orderfulfilled topic
6. The orderfulfilled consumer picks this up, persists the orderfulfilled event to Mongo, then updates the original ordercreated event in Mongo to show the order is complete
 a. If at any point here the count of orders created does not match the count of orders fulfilled, processing ends with an appropriate log message
 b. Otherwise, a new ordercreated event is dropped to that topic
 c. Once 10,000 orders have been processed, the app will log this, along with the start, end and elapsed times for the process.

