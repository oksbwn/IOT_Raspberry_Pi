package in.weargenius.main;

import org.eclipse.paho.client.mqttv3.MqttException;

import in.weargenius.mqtt.SendDataUsingMQTT;
import in.weargenius.restapi.RESTCall;

//import in.weargenius.hardware.WaterTemperatureSensor;

public class MainClass {

	public static void main(String[] args) throws MqttException {
		// TODO Auto-generated method stub
		/*
		WaterTemperatureSensor sensor = new WaterTemperatureSensor();
		double temp=sensor.getWaterTemperature();
		if(temp!=0){
			System.out.println("Temperature is: "+ temp+" °C");
		}
		else
			System.out.println("Sensor not found ");
	
	
		RESTCall http = new RESTCall();
		http.sendDataOverRest(45);
		*/
		SendDataUsingMQTT mqtt= new SendDataUsingMQTT();
		mqtt.publish("field1=65");
	}

}
