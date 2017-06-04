package in.weargenius.restapi;
import java.io.IOException;
import java.io.InputStream;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;


public class RESTCall {
	public void sendDataOverRest(int temperature){
		
		Unirest.post("https://api.thingspeak.com/update.json")
		  .header("accept", "application/json")
		  .field("api_key", "0C8704XEZ68UFGB3")
		  .field("field1",temperature)
		  .asJsonAsync(new Callback<JsonNode>() {

		    public void failed(UnirestException e) {
		        System.out.println("The request has failed");
		    }

		    public void completed(HttpResponse<JsonNode> response) {
		         int code = response.getStatus();
		       //  Map<String, String> headers = response.getHeaders();
		         JsonNode body = response.getBody();
		         InputStream rawBody = response.getRawBody();

			        System.out.println(code);
			        System.out.println(body);
			        System.out.println(rawBody);
			        
			        try {
						Unirest.shutdown();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }

		    public void cancelled() {
		        System.out.println("The request has been cancelled");
		    }


		});
	}

}
