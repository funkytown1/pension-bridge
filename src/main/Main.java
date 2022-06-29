package main;
import java.net.Authenticator;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Timer;

/**
 * Class for interfacing with NEST Pension API.
 * Currently processing GET request for opt out data within a date range.
 * TBC: POST requests
 * TODO: abstract HttpRequest out into HttpRequest child classes (HttpRequestService, HttpRequestStatus, HttpRequestResponse) for readability/maintainability
 * @author morrc
 *
 */
public class Main {
	
	public static  HttpHeaders latestHeaders; // Holds latest response for header checking
	
	public static void main(String[] args) {
				
		// 2.4.1 Make a service request
		Authenticator nestAuth = new NestAuth();
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).authenticator(nestAuth).build();
		String employerId = "EMP000525265";
		String fromDate = "2022-05-01"; // YYYY-MM-DD
		String toDate = "2022-06-01";
		String target = "https://ws.nestpensions.org.uk/psp-webservices/employer/v1/opt-out?emp_refno="
				+ employerId + "&fromDate="  + fromDate + "&toDate=" + toDate;
		
		HttpRequest serviceRequest = HttpRequest.newBuilder().uri(URI.create(target)).headers("X-PROVIDER-SOFTWARE","HWLTest", "X-PROVIDER-SOFTWARE-VERSION", "0.1").build(); // How to build a HTTP request (boiler plate)
		client.sendAsync(serviceRequest, HttpResponse.BodyHandlers.ofString()) // send request and receive response as a string
		.thenApply(HttpResponse::headers) // Once HTTP response received, get the body of the response only
		.thenAccept(Main::updateResponse)
		.join();
		
		// Validate that service request returns expected success code
		String status = "x-nest-status";
		String successMessage = "SUCCESS";
		
		if(latestHeaders.allValues(status).get(0).equals(successMessage)) {
			// Get time and location of 2.4.2 request for retrieve status
			String headerEstCompleteTime = "x-exp-tat";
			String headerUriCheckStatus = "location";
			String completionTime = latestHeaders.allValues(headerEstCompleteTime).get(0); // Get the estimated completion time for the request
			String uriCheckStatus = latestHeaders.allValues(headerUriCheckStatus).get(0); // Get the URI for the status request
			
			System.out.println("Service Request SUCCESS.");
			System.out.println("Expected ETA: " + latestHeaders.allValues(headerEstCompleteTime).get(0));
			
			// Prep for status HttpRequest to be sent at estimated completion time
			Date statusRequestStart = parseToDate(completionTime);
			URI checkStatus = null;
			
			try {
				checkStatus = new URI(uriCheckStatus);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			// Create the HttpRequest for status 2.4.2 & add it to the ScheduleUsingTimerTask thread to await est. completion time
			Timer timer = new Timer();
			HttpRequest statusRequest = HttpRequest.newBuilder().uri(checkStatus).headers("X-PROVIDER-SOFTWARE","HWLTest", "X-PROVIDER-SOFTWARE-VERSION", "0.1").build();
			System.out.println("About to run timer scheduler");
			timer.schedule(new ScheduleUsingTimerTask(client, statusRequest, Request.STATUS), statusRequestStart); // Schedule the task to run at est completion time

			// Check if status code is 201 Created and prepare for HttpRequest for response 2.4.3
			System.out.println("Its over.");
		}
			
			
		}
	
	/**
	 * Returns a Date object corresponding to the time referenced. N.B Calendar object not accepted in Timer.schedule so must use Date.
	 * @param headerEstCompleteTime - containing notation for time in format e.g. 09/05/2022 22:54
	 * @return
	 */
	@SuppressWarnings("deprecation") // Suppressed as requirement specifically for Date object within the resultant use of this method.
	private static Date parseToDate(String time) {
		int year;
		int month;
		int day;
		int hours;
		int minutes;
		
		// Split argument into ints corresponding to day, month, year, hours, minutes
		String[] splitDateTime = time.split(" ");
		String[] dayMonthYear = splitDateTime[0].split("/");
		String[] hoursMinutes = splitDateTime[1].split(":");
		
		// Assign each date component
		day = Integer.valueOf(dayMonthYear[0]);
		month = Integer.valueOf(dayMonthYear[1]) - 1;
		year = Integer.valueOf(dayMonthYear[2]) -1900;
		hours = Integer.valueOf(hoursMinutes[0]);
		minutes = Integer.valueOf(hoursMinutes[1]);
		
		return new Date(year, month, day, hours, minutes);
	}
	
	/**
	 * Assigns the response from the HttpRespone header into static variable so the data within can be analysed.
	 * @param response
	 */
	private static void updateResponse (HttpHeaders response) {
		latestHeaders = response;
	}
		
	}


