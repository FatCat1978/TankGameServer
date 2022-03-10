package tank;

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
	
	String name;

	String turretControllerIP;
	String tankControllerIP;
	
	
	public String encode2Json(); //there's info we want to hide from the clients, namely the IP of everyone else
	
	public void decodeFromJson(); //thus, we need custom JSON encoding/decoding! No big deal though.
	
	public void UpdateFromPacket();
}
