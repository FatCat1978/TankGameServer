package serverConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ConnectionManager extends Thread
{
	
	public final static int connectionTimeOut = 5000; //5000ms should be perfect.
	
	public static ArrayList<String> activeIPs = new ArrayList<String>();
	
	
	private ServerSocket serverSocket; //what socket are we on?
	public static final int port = 6066;
	
	public void run()
	{
			try 
			{
				int responses = 0;
				serverSocket = new ServerSocket(port);
				
				System.out.println("Connection manager booted."); //TODO - log4j?
				while(true)
				{
					try
						{
						//todo, logging!
						serverSocket.setSoTimeout(0);//set an infinite timeout for awaiting connections.
						Socket server = serverSocket.accept(); //accept any connections. this Pauses the thread for timeout duration, which is why we keep it
						//as infinite until we get a client connection.
						//if we're here, we HAVE a connection!
						System.out.println("Client connected!");
						serverSocket.setSoTimeout(connectionTimeOut);
						ConnectionFrom(server.getRemoteSocketAddress().toString()); //we have a connection from X IP - add it to the arraylist.
						DataInputStream in = new DataInputStream(server.getInputStream()); //get the stream from the socket we just made.
						DataOutputStream out = new DataOutputStream(server.getOutputStream());//prep the output stream for returning info to the client.
						while(true)
						{
							try {
							String incoming = in.readUTF();//pauses the thread!
							responses++;
							System.out.println("Responses to client connection: " + responses);
							String outgoing = PacketManager.HandlePacket(incoming);
							
							out.writeUTF(outgoing);
							}
							catch(Exception e)
							{
								DisconnectFrom(server.getRemoteSocketAddress().toString());
								break; //this is incredibly ugly and I absolutely hate it.
								//but it works :)
							}
						}
					
					
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
	//for run
	//iterate endlessly
	//accept connection
		//wait for anything from the client
		//form a response! update if the information is NOT null - null means no changes from the last thing sent;
		//basically - we're having fun here. client sends a request as often as it wants, server responds!

	private void DisconnectFrom(String ipstr)
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
