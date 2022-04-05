package serverConnection;

import java.util.Date;
import java.util.Random;

import com.google.gson.Gson;

//as far as important classes go, this is basically as important as the ConnectionManager.
//THIS is how the server takes information from the client, and decides what to return with it.
public class PacketManager
{
	//I've stopped caring about making things neat at this point. I just want this trashpile done.
	//on the bright side, at least this project gave me a superiority complex when it comes to server code.
	public static final int TILE_SIZE = 64;
	public static final int MAP_SIZE = 20; //square. 
	

public static float randFloat(float min, float max) {

    Random rand = new Random();

    return rand.nextFloat() * (max - min) + min;

}
	
	public static String HandlePacket(String incomingPacket, String sentBy)
	{
		String returnedPacket = ""; //the text form of what we're actually going to send to the client.
			
		Gson converter = new Gson(); //how we convert things. textbook stuff.
		
		ClientToServerPacket C2P = converter.fromJson(incomingPacket, ClientToServerPacket.class); //again, no technical difference between these two packets. I guess it helps with typing?
		ServerToClientPacket returned = new ServerToClientPacket();
		//Lobby stuff basically controls what info the client's sending us while it's in the client. look @ lobbyinfo.java for what everything does.
		if(C2P.packetType.equals("lobby")) //should always be the first packet sent from any given IP
		{
			returned.packetType = "lobby";
			LobbyInfo sentPacketContent = converter.fromJson(C2P.packetInfo,LobbyInfo.class);

			LobbyInfo returnedPacketContent = new LobbyInfo();
			returnedPacketContent.StopExistingInLobby = true;
			//see if the IP already has a key or not.
			String tankKey = ""; 
			
			if(ConnectionManager.IpToGameKey.containsKey(sentBy)) //might be a bit confusing, but the key is the value of the hash in ConnectionManager, with the IP as the key to that value
			{
				
				//does this IP already have a key?
				tankKey = ConnectionManager.IpToGameKey.get(sentBy);//we send this back to the client so they know what tank they're controlling.
				if(GameManager.deadKeys.contains(tankKey))
				{
					System.out.println("Giving:" + sentBy + " A new Key due to death!" );
					tankKey = "" + new Date().getTime();
					ConnectionManager.IpToGameKey.put(sentBy, tankKey);
				}
			}
			else
			{
				System.out.println("Giving:" + sentBy + " A new Key.");
				tankKey = "" + new Date().getTime();
				ConnectionManager.IpToGameKey.put(sentBy, tankKey);
				
			}
			
			returnedPacketContent.chosenTankType = sentPacketContent.chosenTankType;
			returnedPacketContent.yourKey = tankKey;
			LobbyManager.addToPool(sentBy);
			
			if(!sentPacketContent.chosenTankType.equals("NO_SELECTION") && sentPacketContent.Ready)
				returnedPacketContent.StopExistingInLobby = true; //DEBUG/TEMPORARY!!
			//SERIOUSLY. TEMPORARY. //Ah. yes. the temporary perm. solution. classic.
			if(!GameManager.TankHash.containsKey(tankKey) && !sentPacketContent.chosenTankType.equals("NO_SELECTION") && sentPacketContent.Ready && !GameManager.deadKeys.contains(tankKey))
			{
				//make a new tank.
				
				TankInfoPacket t = new TankInfoPacket();
				
				t.TankCreatedAt = new Date().getTime();
				
				float nx = randFloat(100,900);
				float ny = randFloat(100,900);
				
				t.initNew(sentPacketContent.chosenTankType, tankKey,  nx, ny );
				t.size = sentPacketContent.chosenTankType;
				t.x = (TILE_SIZE*MAP_SIZE)/2;
				t.y= (TILE_SIZE*MAP_SIZE)/2;
				//System.out.println("T.SIZE:" + t.size);
				GameManager.TankHash.put(tankKey, t);
			}
			//if we're not, add ourselves, make a key.
			//register that key with the IP
			//and send over all the junk
			returned.packetInfo = converter.toJson(returnedPacketContent);
		}
		
		//Ingame is basically just "take the tank the client sent, overwrite what we have about the client."
		if(C2P.packetType.equals("ingame"))
		{
			//we entirely trust the client in regards to ingame packets. meaning EVERYTHING they send about the tank? it's 
			//the TRUTH. even if it's not.
			returned.packetType = "ingame";
			
			GameStateSend returnedToClient = new GameStateSend();
			
			TankInfoPacket fromClient = converter.fromJson(C2P.packetInfo, TankInfoPacket.class);
			TankInfoPacket CurrentOnServer = GameManager.TankHash.get(ConnectionManager.IpToGameKey.get(sentBy));
			String oldSize = GameManager.TankHash.get(ConnectionManager.IpToGameKey.get(sentBy)).size;
			if(oldSize != null)
				fromClient.size = oldSize; 
			
			if(!GameManager.deadKeys.contains(ConnectionManager.IpToGameKey.get(sentBy))) //if it's dead we stop caring instantly.
				GameManager.TankHash.put(ConnectionManager.IpToGameKey.get(sentBy),fromClient);
			
			returnedToClient.allGameTanks = GameManager.TankHash;
			//see if we're dead.
			if(CurrentOnServer.dead)
			{
				System.out.println("FORCING THEIR UPDATE! THEY DIED!!");
				returnedToClient.youDied = true;
				returnedToClient.forceUpdateTank = true;
			}
			
			
			returnedToClient.allTankGraveLocs = GameManager.graveLocs;
			
			returned.packetInfo = converter.toJson(returnedToClient); //when updating, the client ignores it's own tank!
			//we don't need a "complicated" holder class here. we probably would if PROJECTILES ever got DONE
			
		}
		
		returnedPacket = converter.toJson(returned); //convert the returned packet we've been adding to this entire time to a string via gson
		return returnedPacket;//annnnd send it out.
	}

	
}
