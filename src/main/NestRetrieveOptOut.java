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

public class NestRetrieveOptOut {

}
