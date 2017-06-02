
package in.weargenius.mqtt;

import java.io.IOException;
import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;


public class SendDataUsingMQTT implements MqttCallback {
	private MqttClient client;
	private boolean	quietMode=false;
	private MqttConnectOptions	conOpt;


	public static void main(String[] args) throws MqttException {

		new SendDataUsingMQTT("tcp://mqtt.thingspeak.com:1883", 
				//"ExamplePublish",null,null,false,"channels/281567/publish/LVSBKQWJI3JL293Y","temperature=67",2);
				"ExamplePublish",null,null,false,"channels/281567/publish/QLLE0PQCF3WTH0LR","temperature=67",2);

		//Default settings:
		
	}

    public  SendDataUsingMQTT(String brokerUrl, String clientId, String userName, String password,boolean pubsub,String channel,String data,int qos) throws MqttException {
    	
    	String tmpDir = System.getProperty("java.io.tmpdir");
    	MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

    	try {
    		// Construct the connection options object that contains connection parameters
    		// such as cleanSession and LWT
	    	conOpt = new MqttConnectOptions();
	    	conOpt.setCleanSession(true);
	    	if(password != null ) {
	    	  conOpt.setPassword(password.toCharArray());
	    	}
	    	if(userName != null) {
	    	  conOpt.setUserName(userName);
	    	}

    		// Construct an MQTT blocking mode client
			client = new MqttClient(brokerUrl,clientId, dataStore);

			// Set this wrapper as the callback handler
	    	client.setCallback(this);

		} catch (MqttException e) {
			e.printStackTrace();
			log("Unable to set up client: "+e.toString());
			System.exit(1);
		}
    	if(pubsub)
    		publish(channel,qos,data.getBytes());
    	else
    		subscribe(channel,qos);
    }

    public void publish(String topicName, int qos, byte[] payload) throws MqttException {

    	// Connect to the MQTT server
    	//log("Connecting to "+brokerUrl + " with client ID "+client.getClientId());
    	client.connect(conOpt);
    	log("Connected");

    	String time = new Timestamp(System.currentTimeMillis()).toString();
    	log("Publishing at: "+time+ " to topic \""+topicName+"\" qos "+qos);

    	// Create and configure a message
   		MqttMessage message = new MqttMessage(payload);
    	message.setQos(qos);


    	client.publish(topicName, message);

    	// Disconnect the client
    	client.disconnect();
    	log("Disconnected");
    }

    public void subscribe(String topicName, int qos) throws MqttException {

    	// Connect to the MQTT server
    	client.connect(conOpt);
    	//log("Connected to "+brokerUrl+" with client ID "+client.getClientId());


    	log("Subscribing to topic \""+topicName+"\" qos "+qos);
    	client.subscribe(topicName, qos);

    	// Continue waiting for messages until the Enter is pressed
    	log("Press <Enter> to exit");
		try {
			System.in.read();
		} catch (IOException e) {
			//If we can't read we'll just exit
		}

		// Disconnect the client from the server
		client.disconnect();
		log("Disconnected");
    }

    private void log(String message) {
    	if (!quietMode) {
    		System.out.println(message);
    	}
    }

	public void connectionLost(Throwable cause) {
	//	log("Connection to " + brokerUrl + " lost!" + cause);
		System.exit(1);
	}
	
	public void messageArrived(String topic, MqttMessage message) throws MqttException {
		
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" +time +
                           "  Topic:\t" + topic +
                           "  Message:\t" + new String(message.getPayload()) +
                           "  QoS:\t" + message.getQos());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}


}