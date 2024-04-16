package org.acme.sub.mqttUtils;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jboss.logging.Logger;

import org.acme.sub.model.WeatherData;
import org.acme.sub.repository.WeatherDataRepository;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Singleton
public class MQTTsubscriber implements MqttCallback {

    //@ConfigProperty(name = "mqtt.broker.url")
    //private String mqttBrokerURL;

    //@ConfigProperty(name = "mqtt.topic")
    //private String mqttTopic;

    private static final String MQTT_BROKER = "tcp://localhost:1883"; // Indirizzo del broker MQTT
    private static final String TOPIC = "weather-data"; // Il topic a cui sottoscriversi
    private static final String CLIENT_ID = "WeatherSubscriber"; // ID del client MQTT

    private MqttClient mqttClient;

    private static final Logger LOG = Logger.getLogger(MQTTsubscriber.class);

    @Inject
    WeatherDataRepository weatherDataRepository;
    
    public MQTTsubscriber() {
        try {

            mqttClient = new MqttClient(MQTT_BROKER, CLIENT_ID, new MemoryPersistence());
            mqttClient.setCallback(this);
            
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // Connect
            mqttClient.connect(connOpts);

            // Subscribe
            subscribeToTopic();

        } catch (MqttException e) {
            LOG.infof("Error while connecting to the client", e);
        }
    }

    // Subscribing fn
    private void subscribeToTopic() {
        try {

            int qos = 1; // 1 delivery guarantee

            // Subscribe
            mqttClient.subscribe(TOPIC, qos);
            LOG.info("Subscribed to topic: " + TOPIC);

        } catch (MqttException e) {
            LOG.errorf("Error while subscribing to the topic: %s", TOPIC, e);
        }
    }

    // Error Handling
    @Override
    public void connectionLost(Throwable cause) {
        LOG.errorf("Lost connection to the broker MQTT: %s", cause.getMessage());
    }

    // Checking delivery
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        byte[] payload = message.getPayload();
        String messageContent = new String(payload);
        LOG.infof("Message received on topic" + topic + ": " + messageContent);

        // Registering data in db
        WeatherData data = new WeatherData(topic, messageContent);
        weatherDataRepository.persist(data);
    }

    // Checking delivery completion
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deliveryComplete'");
    }

    

}