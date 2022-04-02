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

public class ConnectedClient implements Runnable //it turns OUT that I was lied to. you need to explicitly make threads in order to do a concurrent connection.
//which frankly makes me wonder why it was suggested in the first place, but at this point any development on this branch is done out of spite.
//I'll show them.... I'll show them all...
{

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
			while(true)
			{
				try {
				String incoming = inStream.readUTF();//pauses the thread!
				String outgoing = PacketManager.HandlePacket(incoming, clientSocket.getRemoteSocketAddress().toString());
				
				
				out.writeUTF(outgoing);
				}
				catch(Exception e)
				{
					ConnectionManager.DisconnectFrom(IP);
					break; //this is incredibly ugly and I absolutely hate it.
					//but it works :) //not anymore! thread time!
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //get the stream from the socket we just made.
		

    
    }
}