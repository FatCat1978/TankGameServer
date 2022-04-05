package serverConnection;

public class TankInfoPacket
{
	int health = 0 ;
	int healthmax = 0;
	String key = "";
	String size = "ERROR";
	float x;
	float y;
	
	float turretAngle = 0;
	float tankAngle = 0;
	public void initNew(String chosenTankType, String newkey) //TODO, rework!
		{
			x = 500;
			y = 500;
			
			healthmax = 500;
			health = healthmax;
			
			size = chosenTankType;
			key = newkey;
			// TODO Auto-generated method stub
			
		}

}
//This is simply another holder class for the client/server communication protocol.
//It's basically just a simplified version of the tank. written before I had enough dealing with Jared's inferno tier tank classes and rewrote it on the client.
//I will never know why each tank needed to be 3 independent classes. 