package main;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author cmorr
 *
 */
public class NestAuth extends Authenticator {
	
	public PasswordAuthentication getPasswordAuthentication() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = input.nextLine();
		System.out.println("Enter password: ");
		char[] password = input.nextLine().toCharArray();
		return new PasswordAuthentication(username, password);
		
	}

}
