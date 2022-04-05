package serverConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager //this was supposed to be a parallel to the client's gamestate. you know. where it stores all the information and everything like that.
//there was nothing to store here though because the client never got to a proper state lmao. if only.
{
	//public static ArrayList<TankInfoPacket> AllTanks = new ArrayList<TankInfoPacket>(); - don't need an arraylist, we already have a hash.
	public static HashMap<String,TankInfoPacket> TankHash = new HashMap<String,TankInfoPacket>(); //stored all the tanks one spot.
	
	//not even an arraylist of projectiles. because hey! Never got DONE
	//I wish this was a solo assignment.
	
	
}
