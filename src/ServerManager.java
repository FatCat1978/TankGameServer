import serverConnection.ConnectionManager;

public class ServerManager
{

	//TODO
		//get logging setup
		//fuck around with the client once Jared's done so I can work on the connections
		
	public static GameManager gameState; 
	
	public static ConnectionManager connections = new ConnectionManager();
	
	public static void main(String[] args)
		{
			connections.run();
		}

}
