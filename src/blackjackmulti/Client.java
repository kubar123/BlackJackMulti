/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackmulti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Jakub Rybicki
 */
public class Client extends Thread {
	private static Socket serverSocket = null;
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	
	public static void main(String[] args) {
		String serverHostname = "127.0.0.1"; // fallback to localhost
		int serverPort = 8080;
		//if(args.length > 0) {
		//	serverHostname = args[0];
		
		boolean connected = connect(serverHostname, serverPort);
		if(!connected) {
			System.err.println("Failed to connect to the server.");
			System.exit(1);
		}
		
		Thread listener = new Client(); // listen to all server messages
		listener.start();
		
		Scanner scan = new Scanner(System.in);
		while (true) {
			while (scan.hasNext()) {
				sendMessage(scan.nextLine());
			}
		}
		
		//scan.close();
	}

	// Called when client is sending a message to the server
	public static void sendMessage(String message) {
		out.println(message);
	}

	// Called when the client has received a message from the server
	public void receiveMessage(String message) {
		System.out.println(message);
	}
	
	public static boolean connect(String hostname, int port) {
		boolean connected = false;

		try {
			serverSocket = new Socket(hostname, port);
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			connected = true;
		} catch (UnknownHostException e) {
			System.err.printf("Unable to find host: %s\n", hostname);
		} catch (IOException e) {
			System.err.printf("Unable to connect to host: %s:%d\n", hostname, port);
		}
		
		return connected;
	}
	
	public static void disconnect() {
		try {
			serverSocket.close();
			out.close();
			in.close();
			
			System.out.println("Successfully disconnected.");
		} catch (IOException e) {
			System.err.println("Unable to disconnect from server.");
		}
	}
	
	public void run() {
		System.out.println("Listening...");
		// read new messages
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				receiveMessage(inputLine);
			}
		} catch(SocketException e) {
			System.err.println("Server connection dropped.");
		} catch (IOException e) {
			System.err.println("Server connection dropped.");
		}

		// Disconnect socket
		disconnect();
		System.exit(0);

		// Kill the thread
		/*try {
			join();
		} catch (InterruptedException e) {
			System.err.println("Unable to close thread.");
		}*/
		
		
	}
}