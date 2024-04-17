# *MQTTapp*

A simple Quarkus app with Java & MongoDB, performing MQTT publish-subscribe messages on weather data retrieved from [`openweathermap API`](https://openweathermap.org/).

## Overview :mag_right:
This app is composed of three microservices, the first [`/publisher`](/MQTTapp/publisher) it's responsible for both the retrieval of the weather data from [`openweathermap API`](https://openweathermap.org/) and the publishing of the data onto the mosquitto MQTT broker,
the second, the [`/subscriber`](/MQTTapp/subscriber) aim to read the data by subscribing to a ['weather-data'] topic, and contestually save them into a dedicate collection on MongoDB. 
The latter is the [`/data-service`](/MQTTapp/data-service), which is responsible of performing operations on the db data.

## Requirements :pushpin:
- Java
- Quarkus
- Mosquitto (or any equivalent MQTT broker)
- MQTT Explorer (Optional, to explore the broker while messages are published)
- MongoDB
- Api Key from  [`openweathermap API`](https://openweathermap.org/) to perform calls

> [!IMPORTANT]
> In the [`/publisher`](/MQTTapp/publisher) microservice in this repo is missing an `application-properties` text file, conveniently ignored since it accommodates the value of the api Key that is supposed to remain confidential.
> The file have to be putted inside of a `/resources`, folder.
> Each of the microservices must have this type of folder hosting the `application.properties` file.
> example:
> The path for the `application.properties` file in the [`/publisher`](/MQTTapp/publisher) microservice will be of this type
> 
```cmd
C:publisher\src\main\java\org\acme\pub\resources> 
```
> To correctly set the `application.properties` file for the [`/publisher`](/MQTTapp/publisher) microservice insert the following configurations:
```cmd
# Scheduler Configs
openweathermap.api.key: the_api_key_provided
weather.scheduler.frequency = 1m 
openweathermap.cities = London,Paris,Rome,Berlin,New York

# Broker Configs
mqtt.broker.url = broker_address
mqtt.topic = "your_topic"
```
