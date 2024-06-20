package org.acme.pub.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.logging.Logger;
import org.acme.pub.mqttUtils.MQTTpublisher;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.quarkus.scheduler.Scheduled;
import java.util.List;
import java.util.Arrays;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;

@Singleton
public class WeatherService {

    final Logger LOG = Logger.getLogger(WeatherService.class);

    // Recalling properties 
    @ConfigProperty(name = "openweathermap.cities")
    public
    String[] cities;

    @ConfigProperty(name = "openweathermap.api.key")
    String apiKey;

    //@ConfigProperty(name = "mqtt.broker.url")
    //String mqttBrokerURL;

    //@ConfigProperty(name = "mqtt.topic")
    //String mqttTopic;

    // Referencing the Publisher class
    @Inject
    MQTTpublisher mqttPublisher;

    // Main fn     
    public void fetchWeatherData() {
        LOG.info("Starting weather data retrieval");
                
        try {     
            fetchWeatherDataForCities();
            LOG.infof("#### WEATHER DATA RETRIEVED SUCCESSFULLY ####");
  
            } catch (Exception e) {
            // Error handling in case of failure while retrieving data
                LOG.error("Error while retrieving weather data from OpenWeather API", e);
            } 
        }       
    
        // Fetching Data fn (for each city provided in the list)
        @Scheduled(every = "${weather.scheduler.frequency}")
        public void fetchWeatherDataForCities() {

            // Cities List
            List<String> cityList = Arrays.asList(cities);

            // Looping through cities
            for (String city : cityList) {
                // Building custom URL
                String apiURL = buildWeatherApiURL(city, apiKey);

                Client client = ClientBuilder.newClient();
                String weatherData = client.target(apiURL)
                        .request(MediaType.APPLICATION_JSON)
                        .get(String.class);
                
                LOG.infof("\nWeather data for %s:\n", city, weatherData);
                               
                // Printing values
                System.out.println(weatherData);

                // Publishing weather data on the MQTT broker
                mqttPublisher.publish(weatherData); 
            }
        }

        // Building fn for the API URL
        private String buildWeatherApiURL(String city, String apiKey) {
            return String.format("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey);
        }
    
    }

    /**
     * An Hardcoded solution
     * may be provided by manually defining the api_key & api_URL values
     * private static final String API_KEY = "43f690f3199a59d0291e0aff041540c6";
     * private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=London&appid=" + API_KEY;
     */

    