/**
 * 
 */
package main;

import java.net.Authenticator;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

/**
 * @author Christopher Morrison
 * 	This class is intended to encapsulate all higher level, common to all, data for NEST requests.
 *
 */
public abstract class NestRequest {
	
	// Constant values
	public static final String URI_PREFIX = "https://ws.nestpensions.org.uk/psp-webservices/employer/v1/";
	public static final String PROVIDER_SOFTWARE_NAME_HEADER = "X-PROVIDER-SOFTWARE";
	public static final String PROVIDER_SOFTWARE_NAME_VALUE = "CM Test";
	public static final String PROVIDER_SOFTWARE_VERSION_HEADER = "X-PROVIDER-SOFTWARE-VERSION";
	public static final String PROVIDER_SOFTWARE_VERSION_VALUE = "0.1";
	
	// Instance variables
	private HttpClient client;
	private HttpHeaders latestHeaders; // Holds latest response for header checking
	private HttpResponse<String> response; // Holds latest response entirely (testing needed)
	private String employerId;
	
	public NestRequest(String employerId, HttpClient client) {
		setEmployerId(employerId);
		this.setClient(client);
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
	
	/**
	 * @return the client
	 */
	public HttpClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}

	public abstract boolean initiateRequest();
	
}
