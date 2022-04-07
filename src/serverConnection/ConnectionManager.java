package serverConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

//Alright, the connectionManager class is by every metric, the big cheese.
//it's solely in charge of waiting for an incoming client, making a thread out of it, and storing it.
public class ConnectionManager extends Thread
{
	
	public final static int connectionTimeOut = 5000; //5000ms should be perfect.
	
	public static ArrayList<String> activeIPs = new ArrayList<String>(); //not used anywhere. this 
	//was originally planned so I can have a IPtoID kinda hash, for usernames.
	//never implemented on the client:tm:
	
	public static HashMap<String,String> IpToGameKey= new HashMap<String,String>();
	
	private ServerSocket serverSocket; //what socket are we on?
	public static final int port = 6066; //hardcoded. I didn't even really see a reason to make this a launch option.
	//I could've done more in regards to making it auto-iterate if a server is already open, but there wasn't really a reason to.
	
	public void run()
	{
			try 
			{
				serverSocket = new ServerSocket(port);
				serverSocket.setSoTimeout(0);//set an infinite timeout for awaiting connections.
				
				System.out.println("Connection manager booted."); //TODO - log4j?. Would've been nice. No time, and it would've really changed nothing overall.
				//I'm incredibly bitter about how this assignment turned out, if you couldn't tell.
				while(true)
				{
					try
						{
						//todo, logging!
						
						Socket server = serverSocket.accept(); //accept any connections. this Pauses the thread for timeout duration, which is why we keep it
						//as infinite until we get a client connection.
						//if we're here, we HAVE a connection!
						System.out.println("Client connected with IP: " + server.getRemoteSocketAddress().toString());
						ConnectionFrom(server.getRemoteSocketAddress().toString()); //we have a connection from X IP - add it to the arraylist.
						new Thread(new ConnectedClient(server)).start(); // Put to a new thread.
						//I WISH I KNEW IT WASNT IMPLICIT LIKE I THOUGHT. THAT WOULD HAVE SAVED SO MUCH TIME.
						}
					catch(SocketTimeoutException ste)
						{
						System.out.println("SocketTimeoutE");
						}
					catch(SocketException se)
						{
						System.out.println("SocketException");
						}
				}
			}
			catch(IOException e)
				{
				e.printStackTrace();
				}
	
	

	}

	static void DisconnectFrom(String ipstr) //called from inside a  thread - has package level sharing.
	//functionally useless, but at least I can reuse this code in the future if I wanted to.
		{
			System.out.println("DISCONNECT FROM:" + ipstr);
			if(activeIPs.contains(ipstr))
				activeIPs.remove(ipstr);
		}

	private void ConnectionFrom(String ipstr)
		{
			if(!activeIPs.contains(ipstr))
				activeIPs.add(ipstr);
			
		}
	
	
	//close connection after lack of response from the player! //no idea why this was sitting at the bottom, but that's done in the connectedClient thread via a timeout.
	
	
}
