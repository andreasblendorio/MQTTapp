# *MQTTapp*

A simple Quarkus app with Java & MongoDB, performing MQTT publish-subscribe messages on weather data retrieved from [`openweathermap API`](https://openweathermap.org/).

## Overview :mag_right:
This app is composed of three microservices, the first [`publisher`](/MQTTapp/publisher) it's responsible for both the retrieval of the weather data from [`openweathermap API`](https://openweathermap.org/) and the publishing of the data onto the mosquitto MQTT broker,
the second, the [`subscriber`](/MQTTapp/subscriber) aim to read the data by subscribing to a ['weather-data'] topic, and contestually save them into a dedicate collection on MongoDB. 
The latter is the [`data-service`](/MQTTapp/data-service), which is responsible of performing operations on the db data.

## Requirements :pushpin:
- Java
- Quarkus
- Mosquitto (or any equivalent MQTT broker)
- MQTT Explorer (Optional, to explore the broker while messages are published)
- MongoDB
