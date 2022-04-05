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
