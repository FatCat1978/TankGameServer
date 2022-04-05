package gameObjects;


public class Tank
{
	int health;
	
	float x;
	float y;
	float angle;
	
	float width;
	float height;
	
	float turretAngle;
	
	long lastShot;
	
	String tankType;

	String turretControllerIP;
	String tankControllerIP;
	
	
	public String encode2Json()
		{
		return null;
	} //there's info we want to hide from the clients, namely the IP of everyone else
	
	public void decodeFromJson()
		{
	} //thus, we need custom JSON encoding/decoding! No big deal though.
	
	public void UpdateFromPacket()
		{
	}
}

//Never actually used, though this one is on me. I gave up on bothering to try and verify tanks serverside (didn't have much to work with)
//soooo this is unused. we just store the tankinfopackets directly instead of bothering to convert.
//sigh.