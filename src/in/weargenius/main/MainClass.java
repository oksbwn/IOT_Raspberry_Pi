package in.weargenius.main;

import in.weargenius.hardware.WaterTemperatureSensor;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WaterTemperatureSensor sensor = new WaterTemperatureSensor();
		double temp=sensor.getWaterTemperature();
		if(temp!=0){
			System.out.println("Temperature is: "+ temp+" °C");
		}
		else
			System.out.println("Sensor not found ");
	}

}
