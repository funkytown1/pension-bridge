/**
 * 
 */
package main;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

/**
 * @author Christopher Morrison
 * 	This class is intended to encapsulate all higher level, common to all, data for NEST requests.
 *
 */
public class NestRequest {
	
	// Constant values
	public static final String URI_START = "https://ws.nestpensions.org.uk/psp-webservices/employer/v1/";
	
	// Instance variables
	private HttpHeaders latestHeaders; // Holds latest response for header checking
	private HttpResponse<String> response; // Holds latest response entirely (testing needed)
	private String employerId;
	
	public NestRequest(String employerId) {
		setEmployerId(employerId);
	}
	
	/**
	 * @return the latestHeaders
	 */
	public HttpHeaders getLatestHeaders() {
		return latestHeaders;
	}


	/**
	 * @param latestHeaders the latestHeaders to set
	 */
	public void setLatestHeaders(HttpHeaders latestHeaders) {
		this.latestHeaders = latestHeaders;
	}


	/**
	 * @return the response
	 */
	public HttpResponse<String> getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpResponse<String> response) {
		this.response = response;
	}


	/**
	 * @return the employerId
	 */
	public String getEmployerId() {
		return employerId;
	}


	/**
	 * @param employerId the employerId to set
	 */
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}
	
}
