package org.acme.pub.mqttUtils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishSample {

   // Main entry point
   public static void main(String[] args) {

       String broker = "tcp://10.100.115.185:1883";
       String topic = "mqtt/test";
       String username = "emqx";
       String password = "public";
       String clientid = "publish_client";
       String content = "Hello MQTT";
       int qos = 0;

       try {

           MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
           MqttConnectOptions options = new MqttConnectOptions();
           options.setUserName(username);
           options.setPassword(password.toCharArray());
           options.setConnectionTimeout(60);
           options.setKeepAliveInterval(60);

           // Connect
           client.connect(options);

           // Create message and setup QoS
           MqttMessage message = new MqttMessage(content.getBytes());
           message.setQos(qos);

           // Publish message
           client.publish(topic, message);
           System.out.println("Message published");
           System.out.println("topic: " + topic);
           System.out.println("message content: " + content);

           // Disconnect
           client.disconnect();

           // Close 
           client.close();
       } catch (MqttException e) {
           throw new RuntimeException(e);
       }
  }
}
