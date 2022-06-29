package main;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.TimerTask;

/**
 * 
 */

/**
 * Custom class for timing the retrieve status (2) and retrieve response tasks for HTTP GET requests to NEST.
 * @author cmorr
 *
 */
public class ScheduleUsingTimerTask extends TimerTask {
	
	private HttpClient client;
	private HttpRequest request;
	private Request requestType;
	
	public ScheduleUsingTimerTask(HttpClient client, HttpRequest request, Request requestType) {
		this.client = client;
		this.request = request;
		this.requestType = requestType;
	}
	
	@Override
	public void run() {
		 System.out.println("Timer task executed :: " + new Date() + " :: " + Thread.currentThread().getName());
		  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
		 .thenApply(HttpResponse::headers)
		 .thenAccept(System.out::println)
		 .join();
		    
		 if(statusHeaderSuccess()) {
			System.out.println("Status Response SUCCESS");
		 } else {
			 System.out.println("Status Response FAIL.");
			 // Get new expected time and re-run
		 }
	}
	
	/**
	 * Returns true if the Nest status is success.
	 * @param request
	 * @return
	 */
	private boolean statusHeaderSuccess() {
		String successHeader = "X-NEST-STATUS";
		String status = request.headers().allValues(successHeader).get(0);
		return status.equals("SUCCESS");
	}

}
