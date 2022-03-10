import tank.Tank;

public class GameManager extends Thread
{
	int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	long lastTick; //when was the last tick? used for determining deltaTime
	

	long GameStarted;
	
	long GameEndTime;
	
	
	String QueuedPacket; //updated every tick! this is what's actually sent
	
	//ArrayList of Tanks
	//ArrayList of Projectiles;
	
	
	public Tank getTankByName(String name)
	{
			return null;
	}
	
	public void UpdateTankFromPacket(ClientToServerPacket incoming)
	{
			
	}
	
	public void run()
	{
			//tick @ whatever rate we decide on - shouldnt matter for the client
	}
	//called every tick
	private void Update()
	{
			
	}
	
	private boolean CanWeStart()
	{
			//we need at least 1 staffed tank to run!
			return false;
	}
		
}
