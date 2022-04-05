package serverConnection;

//alright, this is another one of those simple "information holder" classes that're basically entirely used with gson.
//basically, what we send back to the player IF they were in the lobby.

public class LobbyInfo
{
	public boolean Ready = false; //do we spawn the tank??
	public String chosenTankType = ""; //sent TO the server
	public String connectedPlayers = ""; //gotten FROM the server
	public String yourKey = ""; //FROM the server.
	public boolean GameInProgress = false; //From the server
	public int RequiredPlayers = 0; //from the server. 
	public boolean StopExistingInLobby = false; //tells the client we're shifting to a REAL BOY GAME
}
