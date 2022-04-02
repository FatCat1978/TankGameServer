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

public class ConnectionManager extends Thread
{
	
	public final static int connectionTimeOut = 5000; //5000ms should be perfect.
	
	public static ArrayList<String> activeIPs = new ArrayList<String>();
	public static HashMap<String,String> IpToGameKey= new HashMap<String,String>();
	
	private ServerSocket serverSocket; //what socket are we on?
	public static final int port = 6066;
	
	public void run()
	{
			try 
			{
				serverSocket = new ServerSocket(port);
				serverSocket.setSoTimeout(0);//set an infinite timeout for awaiting connections.
				
				System.out.println("Connection manager booted."); //TODO - log4j?
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
						//I WISH I KNEW IT WASNT IMPLICIT LIKE I THOUGHT.
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
		{
			if(activeIPs.contains(ipstr))
				activeIPs.remove(ipstr);
			
		}

	private void ConnectionFrom(String ipstr)
		{
			if(!activeIPs.contains(ipstr))
				activeIPs.add(ipstr);
			
		}
	
	
	//close connection after lack of response from the player!
	
	
}
