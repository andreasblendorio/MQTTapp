package org.acme.pub.mqttUtils;

import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

@Singleton
public class MQTTpublisher {

    static String mosquittoHost = System.getenv("MOSQUITTO_HOST");
    static String mqttTopic = System.getenv("MQTT_TOPIC");

    // MQTT Broker address
    private static final String MQTT_BROKER = mosquittoHost != null 
            ? "tcp://" + mosquittoHost + ":1883" 
            : "tcp://localhost:1883";
    
    // Topic for weather-data publishing
    private static final String TOPIC = mqttTopic != null 
            ? mqttTopic 
            : "weather-data";

    // MQTT Client ID
    private static final String CLIENT_ID = "WeatherService";

    // Client
    private MqttClient mqttClient;

    private static final Logger LOG = Logger.getLogger(MQTTpublisher.class);

    public MQTTpublisher() {
        LOG.info("MOSQUITTO_HOST: " + System.getenv("MOSQUITTO_HOST"));
        LOG.info("MQTT_TOPIC: " + System.getenv("MQTT_TOPIC"));

        connectToBroker();
    }

    // Connecting fn
    private void connectToBroker() {
        try {
            mqttClient = new MqttClient(MQTT_BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            mqttClient.connect(connOpts);
            LOG.info("Connected to MQTT broker at " + MQTT_BROKER);
        } catch (MqttException e) {
            LOG.error("Error while connecting to the MQTT broker", e);
        }
    }

    // Publishing fn
    public void publish(String data) {
        try {
            if (!mqttClient.isConnected()) {
                connectToBroker();
            }
            mqttClient.publish(TOPIC, data.getBytes(), 0, false);
            LOG.info("Published data to topic " + TOPIC);
        } catch (MqttException e) {
            LOG.error("Error publishing message to the broker", e);
        }
    }    
}
