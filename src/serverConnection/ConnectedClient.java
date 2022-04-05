package serverConnection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
//thanks to https://stackoverflow.com/questions/50076607/java-concurrent-socket-programming for clearning this up.
//would heavily suggest using that as an example on brightspace in the future, it would've saved a massive amount of debugging 

public class ConnectedClient implements Runnable //it turns OUT that I was lied to. or at least a little bit confused. you need to explicitly make threads in order to do a concurrent connection.
//which frankly makes me wonder why it was suggested in the first place, but at this point any development on this branch is done out of spite.
//I'll show them.... I'll show them all...
//and I did! they did jack all on their attempt at making a server. I wish this was a solo project.
{

	
	//generic info about what the client needs to communicate with the server - or, well, the other way around in this case.
	//everything is basically derived from the clientSocket. textbook stuff.
    private Socket clientSocket;

    private DataOutputStream out; // write for the client
    private BufferedReader in; // read from the client

    private String IP = "";
    
    public ConnectedClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
			clientSocket.setSoTimeout(0); 
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    @Override
    public void run() {
        // Do client process
		try {
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());//prep the output stream for returning info to the client.
			IP = clientSocket.getRemoteSocketAddress().toString(); //last minute bugfix. doesn't really matter though.
			while(true)
			{
				try {
				String incoming = inStream.readUTF();//pauses the thread!
				//packet communication is nothing special. we take a string from the client, hand it over to the PacketManager, and send whatever
				//it tells us to back to the client who sent the string in the first place.
				//Essentially the same as the chat application, but without the whole reconstructing the connection part every time.
				//...and with a 30 times per second heartbeat.
				String outgoing = PacketManager.HandlePacket(incoming, clientSocket.getRemoteSocketAddress().toString());
				
				
				out.writeUTF(outgoing);
				}
				catch(Exception e)
				{
					ConnectionManager.DisconnectFrom(IP);
					break; //this is incredibly ugly and I absolutely hate it.
					//but it works :) //not anymore! thread time! //now it does???
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		

    
    }
}