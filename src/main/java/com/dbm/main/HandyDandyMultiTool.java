package com.dbm.main;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Value;

public class HandyDandyMultiTool {
    private final AdminClient client;
   // private static String dbUri = "mongodb+srv://bmcdougald:erngui6368@cluster0.gzzjztn.mongodb.net/ecommerce";
    private static String dbUri = "mongodb://localhost:27017/";
    private static String kafkaUri = "\\centos-8-pc:9092";
    //private static String kafkaUri = "\\localhost:9092";
    private static String dbName = "ecommerce";

    public HandyDandyMultiTool(String bootstrap) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap);
        props.put("request.timeout.ms", 3000);
        props.put("connections.max.idle.ms", 5000);

        this.client = AdminClient.create(props);
    }

	public static void main(String[] args) {
        HandyDandyMultiTool kac = new HandyDandyMultiTool(kafkaUri);
        kac.doKafkaStuff();
        kac.doDBStuff();
	}

    private void doKafkaStuff(){
        try {
			boolean alive = verifyConnection();
			System.out.println("AdminClient is connected: " + alive);
            ListTopicsResult r = client.listTopics();
            Set<String> topicNames = r.names().get();
            System.out.println("Listing topics to be deleted:");
            for (String tn : topicNames){
                System.out.println("Topic name: " + tn);
            }
            if (topicNames != null && topicNames.size() > 0) {
                DeleteTopicsResult dtr = client.deleteTopics(topicNames);
                System.out.println("Aforementioned topics have been deleted:");
            }else{
                System.out.println("No topics found to delete");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDBStuff(){
        System.out.println("Deleting database " + dbName);
        MongoClient mongo = MongoClients.create(dbUri);
        MongoDatabase database = mongo.getDatabase(dbName);
        database.drop();
        System.out.println("Database " + dbName + " deleted");
    }

    public boolean verifyConnection() throws ExecutionException, InterruptedException {
        Collection<Node> nodes = this.client.describeCluster()
          .nodes()
          .get();
        return nodes != null && nodes.size() > 0;
    }
}
