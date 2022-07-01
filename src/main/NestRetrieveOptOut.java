/**
 * 
 */
package main;

/**
 *  * @author Christopher Morrison
 *	Class for handling of the "request to retrieve opt outs" web service call.
 *	The guidance for this can be found in section 3.5 of web-services-api-specification document
 *	published by NEST.
 *
 *	The order of actions in this process are:
 *	- Send GET request to initiate the request (authorisation)
 *	- 202 response = accepted
 *	- response also includes expected time of request completion
 *	- Send GET request for status of processing at indicated time
 *	- 201 response = created
 *	- Send final GET request for opt-out data
 *	- 200 response = OK
 * 	- body of response contains requested data
 * 
 * 	This class is intended to encapsulate the processing of the specific opt out request flow.
 * 	The intention will be that the end response is saved for future reference TBD. database to use?
 */

public class NestRetrieveOptOut extends NestRequest {
	
	private String fromDate, toDate, target;
	
	
	public NestRetrieveOptOut(String employerId, String fromDate, String toDate) {
		super(employerId);
		setFromDate(fromDate);
		setToDate(toDate);
		generateTarget();
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}


	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}


	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}


	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}


	/**
	 * Generates the target
	 */
	private void generateTarget() {
		target = NestRequest.URI_START + "opt-out?emp_refno="
				+ getEmployerId() + "&fromDate="  + fromDate + "&toDate=" + toDate;
	}
	
	/**
	 * Send initial GET request and check response is a success.
	 * Response will not contain the data requested, but an affirmation that the process will commence
	 * and an ETA on when the response will be ready.
	 * This process will be proceeded by checkStatus() method at time of ETA advised.
	 * Returns true if the response status code is 202.
	 * 
	 * TODO add error checking for 4xx and 5xx codes and appropriate handling of same.
	 */
	public boolean initiateRequest() {
		return true;
	}
	
	
	
	
}
