import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

		
	// RECEIVER
	
		// store expected SN number
	
		// check SN of each received packet, if it does not equal the expected SN, do not send an ACK
		// 
	
	
	
	
	// SENDER 
	
		// Include the SN of the last received packet in an ACK
		//
	
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		int serverPort = 49152;
		int expSN = 1;
		
		String packetSNString;
		int packetSN;
		
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		int[] receivedPackets = new int [10];
		
		
		
		int windowSize = 5;
		
		Boolean server = true;
		
		

		long sessionOpen = 0;
		
		
		
		while(server) {
			
			
			

	
			
			System.out.println("Server listening on port: " + serverPort);
			
			// RECEIVE
			
			

		
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			serverSocket.receive(receivePacket);
			String message = new String(receivePacket.getData());
			
			
			// only print session open once after confirming receipt of first ACK
			sessionOpen++;
			
			if (sessionOpen <= 1 ) {
			
			System.out.println("Session open");
			
			}
			
			
			
			// parse SN from packet and compare with expected SN
			
			packetSNString = message.substring(8);
			System.out.println("Sequence number of packet received: " + packetSNString);
			
			
			
			
			
			
			
			// remove hidden character and convert packetSN to int
			packetSNString = packetSNString.replaceAll("[^0-9]+", "");
			packetSN = Integer.parseInt(packetSNString);
			
		
			
			// SEND VARIABLES
			
						
				
						//COMPARE SERVER SN WITH PACKET SN IN HERE SOMEWHERE
						
			
				
						
						
				
			// SEND	
				
				
			if (packetSN == expSN) {
			
				
				
				InetAddress IPAddress = receivePacket.getAddress();
				
				int port = receivePacket.getPort();
					
				
				sendData = packetSNString.getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
	
			
				
				// print data to screen and transmit ACK back to sender
				
				System.out.println("Message received from client: " + message);
				
				
				serverSocket.send(sendPacket);
				
				System.out.println("ACK sent for packet: " + packetSNString);
					
			
				expSN++;
			
			}
			
			
			if (expSN == 31) {
				
				System.out.println("Session closed");
				server = false;
				
			}
			
			
			
			
			} 
			
		}
		
	}



	


