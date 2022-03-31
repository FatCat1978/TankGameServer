package serverConnection;

import java.util.Date;

import com.google.gson.Gson;

public class PacketManager
{
	public static String HandlePacket(String incomingPacket, String sentBy)
	{
		String returnedPacket = "";
			
		Gson converter = new Gson();
		
		ClientToServerPacket C2P = converter.fromJson(incomingPacket, ClientToServerPacket.class);
		ServerToClientPacket returned = new ServerToClientPacket(); //no need to change the packet type here/
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
				
			}
			else
			{
				tankKey = "" + new Date().getTime();
				ConnectionManager.IpToGameKey.put(sentBy, tankKey);
				
			}
			
			returnedPacketContent.yourKey = tankKey;
			LobbyManager.addToPool(sentBy);
			
			returnedPacketContent.StopExistingInLobby = true; //DEBUG/TEMPORARY!!
			//SERIOUSLY. TEMPORARY.
			if(!GameManager.TankHash.containsKey(tankKey))
			{
				//make a new tank.
				TankInfoPacket t = new TankInfoPacket();
				t.initNew(sentPacketContent.chosenTankType, tankKey);
				GameManager.TankHash.put(tankKey, t);
			}
			//if we're not, add ourselves, make a key.
			//register that key with the IP
			//and send over all the junk
			returned.packetInfo = converter.toJson(returnedPacketContent);
		}
		
		if(C2P.packetType.equals("ingame"))
		{
			returned.packetType = "ingame";
			
			TankInfoPacket fromClient = converter.fromJson(C2P.packetInfo, TankInfoPacket.class);
			
			GameManager.TankHash.put(ConnectionManager.IpToGameKey.get(sentBy),fromClient);
			
			returned.packetInfo = converter.toJson(GameManager.TankHash); //when updating, the client ignores it's own tank!
			
			
			
		}
		
		returnedPacket = converter.toJson(returned);
		return returnedPacket;
	}
	
	public static ServerToClientPacket generateErrorPacket(String error)
	{
			return null;
	}
	
	
}
