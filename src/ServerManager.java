import serverConnection.ConnectionManager;
import serverConnection.GameManager;

public class ServerManager
{

	//TODO
		//get logging setup
		//fuck around with the client once Jared's done so I can work on the connections
		
	public static GameManager gameState = new GameManager(); 
	
	public static ConnectionManager connections = new ConnectionManager();
	
	public static void main(String[] args)
		{
			System.out.println("BOOTING:");
			gameState.run();
			connections.run();
		}

}
