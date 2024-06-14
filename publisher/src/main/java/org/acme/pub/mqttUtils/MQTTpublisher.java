package org.acme.pub.mqttUtils;

import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

//import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class MQTTpublisher {
        
    //@ConfigProperty(name = "mqtt.broker.url")
    //private String mqttBrokerURL;

    //@ConfigProperty(name = "mqtt.topic")
    //private String mqttTopic;
    
    // MQTT borker address
    private static final String MQTT_BROKER = "tcp://host.docker.internal:1883"; 
    // Topic to publish weather-data
    private static final String TOPIC = "weather-data"; 
    // MQTT client ID 
    private static final String CLIENT_ID = "WeatherService"; 
    
    private MqttClient mqttClient;

    private static final Logger LOG = Logger.getLogger(MQTTpublisher.class);

    public MQTTpublisher() {
        try {

            mqttClient = new MqttClient(MQTT_BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Connect
            mqttClient.connect(connOpts);

        } catch (MqttException e) {
            LOG.infof("Error while connecting to the client", e);
        }
    }

    // Publishing fn
    public void publish(String data) {
        try {
            mqttClient.publish(TOPIC, data.getBytes(), 0, false);
        } catch (MqttException e) {
            LOG.error("Error publishing message to the broker", e);
        }
    }

    // Publishing fn (Alternative)
    
    //public void publish(String message) {

        //String clientId = MqttClient.generateClientId();
        //try (mqttClient client = new MqttClient(MQTT_BROKER, CLIENT_ID)) {
            
            /**
             * If further options are needed a ConnectOptions class can be created
             * MqttConnectOptions options = new MqttConnectOptions();
             * options.setUserName(username);
             * options.setPassword(password.toCharArray());             * 
             */
                                   
            // Connecting 
            //client.connect();
            //if (client.isConnected()) {
                //client.publish(topic, message.getBytes(), 0, false);
                //LOG.infof("Message published to MQTT topic '%s' ", topic, message);                
            //}     
            
        //} catch (MqttException e) {
            // Error handling in case of failure while publishing data 
            //LOG.error("Error publishing message to MQTT", e);
        //}
    //}    
}
