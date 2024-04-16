package org.acme.sub.repository;

import org.acme.sub.model.WeatherData;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WeatherDataRepository implements PanacheMongoRepository<WeatherData> {
    // This structure will be responsible for accessing the db and performing operations
}

