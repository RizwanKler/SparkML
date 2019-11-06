package com.jobreadyprogrammer.spark;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyCallback implements MqttCallback
{

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
		System.out.println("Connection Lost... reconnecting.");
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {

		
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
		System.out.println(token + " delivery complete.");
		
	}

}
