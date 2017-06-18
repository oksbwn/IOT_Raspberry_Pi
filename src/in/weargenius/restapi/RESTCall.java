package in.weargenius.restapi;
import java.io.InputStream;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;


public class RESTCall implements Callback<JsonNode>{

	public void sendDataOverRest(double temp) {
		
		Unirest.post("https://api.thingspeak.com/update.json")
		  .header("accept", "application/json")
		  .field("api_key", "0C8704XEZ68UFGB3")
		  .field("field1",temp)
		  .asJsonAsync(this);
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
        System.out.println("The request has been cancelled");
	}

	@Override
	public void completed(HttpResponse<JsonNode> response) {
		// TODO Auto-generated method stub
		  int code = response.getStatus();
	       //  Map<String, String> headers = response.getHeaders();
	         JsonNode body =response.getBody();
	         InputStream rawBody = response.getRawBody();

		        System.out.println(code);
		        System.out.println(body);
		        System.out.println(rawBody);
	}

	@Override
	public void failed(UnirestException arg0) {
		// TODO Auto-generated method stub
        System.out.println("The request has failed");
	}

}
