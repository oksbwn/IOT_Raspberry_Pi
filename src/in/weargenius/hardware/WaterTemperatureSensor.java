package in.weargenius.hardware;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;


public class WaterTemperatureSensor {
	public double getWaterTemperature(){
		
        W1Master w1Master = new W1Master();

        System.out.println(w1Master);

        for (TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)) {
            System.out.printf("%-20s %3.1f°C %3.1f°F\n", device.getName(), device.getTemperature(),
                    device.getTemperature(TemperatureScale.CELSIUS));
            if(device.getName().contains("28-0115155154ff"))
            	return device.getTemperature(TemperatureScale.CELSIUS);
        	//return 32;
        }
		return 0;
	}

}
