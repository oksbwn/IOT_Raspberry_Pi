package in.weargenius.mqtt;


import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;


public class SendDataUsingMQTT{
	private MqttClient client;
	private MqttConnectOptions	conOpt;


	String brokerUrl="tcp://mqtt.thingspeak.com:1883";
	String clientId="ExamplePublish";
	String channel="channels/281567/publish/LVSBKQWJI3JL293Y";
	int qos=0;
	 public  SendDataUsingMQTT() throws MqttException {
	    	String tmpDir = System.getProperty("java.io.tmpdir");
	    	MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

	    	try {
	    		// Construct the connection options object that contains connection parameters
	    		// such as cleanSession and LWT
		    	conOpt = new MqttConnectOptions();
		    	conOpt.setCleanSession(true);
		    	//conOpt.
	    		// Construct an MQTT blocking mode client
				client = new MqttClient(brokerUrl,MqttClient.generateClientId(), dataStore);

				// Set this wrapper as the callback handler
				
		    	client.setCallback(new MqttCallback() {
					
					@Override
					public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
						// TODO Auto-generated method stub
						String time = new Timestamp(System.currentTimeMillis()).toString();
						System.out.println("Time:\t" +time +
				                           "  Topic:\t" + arg0 +
				                           "  Message:\t" + new String(arg1.getPayload()) +
				                           "  QoS:\t" + arg1.getQos());
					}
					
					@Override
					public void deliveryComplete(IMqttDeliveryToken arg0) {
						// TODO Auto-generated method stub

			        	try {
							System.out.println(arg0.getMessageId()+" "+arg0.getMessage());
						} catch (MqttException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void connectionLost(Throwable arg0) {
						// TODO Auto-generated method stub

						System.out.println("Connection to " + brokerUrl + " lost!");

						System.out.println("Reconnecting..");
			        	try {
							client.connect(conOpt);
				        	System.out.println("Connected");
						} catch (MqttException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//	System.exit(1);
							//client.
					}
				});
		    	if(client.isConnected()){

		        	System.out.println("was already Connected");
		        	client.disconnect();
		    	}
	        	client.connect(conOpt);
	        	System.out.println("Connected");


			} catch (MqttException e) {
				e.printStackTrace();
				System.out.println("Unable to set up client: "+e.toString());
				System.exit(1);
			}
	    		

	    	
	    }
	    public void publish(String data) throws MqttPersistenceException, MqttException{
	    	String time = new Timestamp(System.currentTimeMillis()).toString();
	    	System.out.println("Publishing at: "+time+ " to topic \""+channel+"\" qos "+qos);

	    	// Create and configure a message
	   		MqttMessage message = new MqttMessage(data.getBytes());
	    	message.setQos(qos);
	    	message.setRetained(false);

	    	client.publish(channel, message);

	    	// Disconnect the client

	    }
	    public void close() throws MqttException{    	
	    	client.disconnect();
	    	System.out.println("Disconnected");
	    }
}
