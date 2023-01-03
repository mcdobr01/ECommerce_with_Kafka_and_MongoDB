# ECommerce_with_Kafka_and_MongoDB
 
This was created as a learning curve for both Kafka messaging and MongoDB.

**Assumptions**
1. You have installed and configued a local Kafka instance
2. You have installed and configured a local PostgreSQL instance

The idea is meant to illustrate a barebones ecommerce example flow:
1. An order is created and submitted to the **_ordercreated_** Kafka topic
1. **Order Created**
   - The **_ordercreated_** consumer picks this up, persists the **_ordercreated_** event in Mongo and drops an event to the **_paymentcompleted_** topic
3. **Payment Completed**
   - The **_paymentcompleted_** consumer picks this up, persists the **_paymentcompleted_** event to Mongo and drops an event to the **_inventoryupdated_** topic
3. **Inventory Updated**
   - The **_inventoryupdated_** consumer picks this up, persists the **_inventoryupdated_** event to Mongo and drops an event to the **_ordershipped_** topic
4. **Order Shipped**
   - The **_ordershipped_** consumer picks this up, persists the **_ordershipped_** event to Mongo and drops an event to the **_orderfulfilled_** topic
5. **Order Fulfilled/Completed**
   - The **_orderfulfilled_** consumer picks this up, persists the **_orderfulfilled_** event to Mongo, then updates the original **_ordercreated_** event in Mongo to show the order is complete
   - If at any point here the count of orders created does not match the count of orders fulfilled, processing ends with an appropriate log message
   - Otherwise, a new **_ordercreated_** event is dropped to that topic
   - Once 10,000 orders have been processed, the app will log this, along with the start, end and elapsed times for the process.
 
A similar project is here: https://github.com/mcdobr01/ECommerce_with_Kafka_and_PostgreSQL

This code does exactly the same, the only difference being the use of PostgreSQL as the back-end data source.

These 2 projects were done to explore the differences between MongoDB and PostgreSQL for
1. A comparison of implementation from a developer's POV
2. A quick and dirty performance comparison to see how long it takes to process 10,000 orders through the defined flow and into the database

