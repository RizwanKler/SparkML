package com.jobreadyprogrammer.spark;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttApp {
	
	public static MqttAsyncClient myClient;
	
	public static void main( String[] args ) throws MqttException
	{
		myClient = new MqttAsyncClient("tcp://192.168.230.49", UUID.randomUUID().toString());
		MyCallback myCallback = new MyCallback();
		myClient.setCallback(myCallback);
		
		IMqttToken token = myClient.connect(); // Asynchronous connection
		token.waitForCompletion(); // Wait until connection is established
		
		myClient.subscribe("rizTopic", 0);
		
		System.out.println("Test the Publisher Subscriber MqttBroker");
	}
	
	
}
