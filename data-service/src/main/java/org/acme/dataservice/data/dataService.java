package org.acme.dataservice.data;

import jakarta.inject.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@Singleton
//@ApplicationScoped
public class dataService {
    
    // DB client
    private MongoClient mongoClient;
    // DB instance
    private MongoDatabase database;
    // DB collection
    private MongoCollection<Document> collection;
     
    // Init
    @Inject
    public dataService(@ConfigProperty(name = "quarkus.mongodb.connection-string") String connectionString,
                       @ConfigProperty(name = "quarkus.mongodb.database-name") String databaseName,
                       @ConfigProperty(name = "quarkus.mongodb.collection-name") String collectionName) {
      this.mongoClient = MongoClients.create(connectionString);
      this.database = mongoClient.getDatabase(databaseName);
      this.collection = database.getCollection(collectionName);
    }

    // City filtering
    public List<Document> getDataByCity(String cityName) {
        return collection.find(Filters.eq("name", cityName)).into(new ArrayList<>());
    }

    // Temperature filtering
    public List<Document> getDataByTemperatureRange(double minTemp, double maxTemp) {
        return collection.find(Filters.and(
                Filters.gte("main.temp", minTemp),
                Filters.lte("main.temp", maxTemp)
        )).into(new ArrayList<>());
    }

    // Latest city data filtering
    public Document getLatestDataByCity(String cityName) {
        return collection.find(Filters.eq("name", cityName))
                .sort(new Document("dt", -1))
                .limit(1)
                .first();
    }

    // Update 
    public void updateDataByCity(String cityName, Document newData) {
        collection.updateOne(
                Filters.eq("name", cityName),
                new Document("$set", newData)
        );
    }

    // Delete
    public void deleteOldData() {
        // Deleting old data
        long cutoffTimestamp = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000); // 30 days ago
        collection.deleteMany(Filters.lt("dt", cutoffTimestamp));
    }

    // Close
    public void close() {
        mongoClient.close();
    }
}

