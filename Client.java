import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Client {
	

	
	static String fileName = "./umbrella.txt";


private static String readUsingScanner(String filename) throws IOException {
		
		
		Path path = Paths.get(filename);
		Scanner scanner = new Scanner(path);
		// System.out.println("Read text file using Scanner");
		
		
		String line = scanner.nextLine();
		// System.out.println(line);
		
		scanner.close();	
		
		return line;
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		// CLIENT VARIABLES
		
		DatagramSocket clientSocket = new DatagramSocket();
		
		
		clientSocket.setSoTimeout(1000);
		
		
		InetAddress IPAddress = InetAddress.getByName(args[0]);
		
		
		int clientPort = Integer.parseInt(args[1]);
		
		
		byte[] sendData = new byte[1024];
		
		
		byte[] receiveData = new byte[1024];
		
		
		Boolean client = true;
		
		
		String message = readUsingScanner(fileName);
		
		
		String received;
		
		
		int SN = 1;
		
		
		int ACK = 0;
		
		
		int lastSentSN;
		
		
		int windowSize = 5;
		
		
		long sessionOpen = 0;
		
	
		
		
		
		
		while (client) {
			
			try {
				
				
				
				
				
				
				// SEND
				
				
				
				for (int i = 0; i < windowSize; i++) {
				
				message = readUsingScanner(fileName);
				message += SN;
				System.out.println("Message sent: " + message);
				sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
				clientSocket.send(sendPacket);
				SN++;
				
			
				
				}
				
				
			
				lastSentSN = SN-1;
				
				
				
				
				
				
				// RECEIVE
				
				
				for (int i = 0; i < windowSize; i++) {
				

				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				received = new String(receivePacket.getData());
				received = received.replaceAll("[^0-9]+", "");
				ACK = Integer.parseInt(received);
				
				// only print session open once after confirming receipt of first ACK
				sessionOpen++;
				
				if (sessionOpen <= 1 ) {
				
				System.out.println("Session open");
				
				}
				
				System.out.println("FROM SERVER: ACK" + ACK);
				
				
				
				// Check if the ACK received was for one of the last five packets sent, 
				// otherwise re-send the last 5 packets
				
				
				if (ACK == lastSentSN) {
					
					
					System.out.println("ACK confirmed for packet: " + ACK);
					//SN++;
						
				}
				
					else if (ACK == lastSentSN - 1) {
					
						System.out.println("ACK confirmed for packet: " + ACK);
					
																							}
				
						else if (ACK == lastSentSN - 2) {
					
							System.out.println("ACK confirmed for packet: " + ACK);
							
																							}
				
							else if (ACK == lastSentSN - 3) {
		
		
								System.out.println("ACK confirmed for packet: " + ACK);
																							}
				
								else if (ACK == lastSentSN - 4) {
		
		
									System.out.println("ACK confirmed for packet: " + ACK);
																							}
				
									else if (ACK == lastSentSN - 5) {
		
		
										System.out.println("ACK confirmed for packet: " + ACK);
																							}
				
				
				// reset SN to SN after the last received ACK
				// In-case packets are out of order etc.
				
				else {
					
					SN = SN - windowSize;
					
					
				}
				
			
				
				
				
				
				// check if session should close
				
				if (ACK == 30) {
					
					client = false;
					System.out.println("Session closed");
					
				}
				
				
				}

		
		
			} catch (SocketTimeoutException exception) {
				
				
				int timeoutRangeStart = SN - windowSize;
				int timeoutRangeEnd = SN-1;
				
				
				
				System.out.println("Timeout on packets " + timeoutRangeStart + " - " + timeoutRangeEnd);
			
				// If no response received from the server then re-send the last 5 packets.
				// THIS IS THE PART THAT MAKES SURE NO PACKET IS LOST 
				
				
				SN = SN-windowSize;
			
	
				
	
				
			}
			
		
			
			
			

		}
		
	
		

	}
	
}
