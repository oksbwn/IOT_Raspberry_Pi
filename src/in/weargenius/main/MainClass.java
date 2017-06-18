package in.weargenius.main;

import org.eclipse.paho.client.mqttv3.MqttException;

import in.weargenius.mqtt.SendDataUsingMQTT;
import in.weargenius.restapi.RESTCall;

import in.weargenius.hardware.WaterTemperatureSensor;

public class MainClass {

	public static void main(String[] args) throws MqttException, InterruptedException {
		// TODO Auto-generated method stub
		
		WaterTemperatureSensor sensor = new WaterTemperatureSensor();
		RESTCall http = new RESTCall();
		SendDataUsingMQTT mqtt= new SendDataUsingMQTT();
		
		double temp;
		while(true){
			temp=sensor.getWaterTemperature();
			
			if(temp!=0){
				System.out.println("Temperature is: "+ temp+" °C");
			}
			else
				System.out.println("Sensor not found ");
	
			if(args[0].equals("mqtt")){
				System.out.println("Sending temp. data using MQTT. ");
				mqtt.publish("field1="+temp);
			}
			else if(args[0].equals("rest")){
				System.out.println("Sending temp. data using REST. ");
				http.sendDataOverRest(temp);
				
			}
			else{
				System.out.println("No interface is selected.");
				System.exit(0);
			}
			Thread.sleep(1*60*1000);
		}
	
	}

}
