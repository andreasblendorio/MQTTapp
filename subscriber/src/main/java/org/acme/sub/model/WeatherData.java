package org.acme.sub.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "weatherdata")

public class WeatherData extends PanacheMongoEntity {
    public String topic;
    public String message;

    public WeatherData(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public WeatherData() {
    }
}
