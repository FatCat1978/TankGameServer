package serverConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameManager extends Thread
{
	final static int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	static long lastTick; //when was the last tick? used for determining deltaTime
	
	static long GameStarted;
	
	static long GameEndTime;
	
	static HashMap<String,String> IPtoID = new HashMap<String,String>();
	static HashMap<String,String> IDtoTank = new HashMap<String,String>();
	public static HashMap<String,TankInfoPacket> TankHash = new HashMap<String,TankInfoPacket>(); //stored all the tanks one spot.
	public static ArrayList<TankGraveLoc> graveLocs = new ArrayList<TankGraveLoc>();
	
	public static ArrayList<String> deadKeys = new ArrayList<String>();
	
	static ArrayList<String> ActiveIPs = new ArrayList<String>();
	
	static String QueuedPacket; //updated every tick! this is what's actually sent, instead of the client being able to request at an arbitrary time.
	//might not be ideal?
	
	boolean inGame = false; //false in lobby, true in game.
		
	public void UpdateTankFromPacket(ClientToServerPacket incoming)
	{
			
	}
	
	public int GetAliveTanks()
	{
			return 1;
	}
	
	public void run()
	{
		System.out.println("Initializing Regular ticker at " + desiredTickRate + " Ticks per second.");
			ScheduledExecutorService TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
			//tick @ whatever rate we decide on - shouldnt matter for the client. their update is independant.
			
	}
	//called every tick
	private void Update()
	{
		Update_Game();
		lastTick = new Date().getTime();
	}
	
	private float dist(float x1,float y1,  float x2, float y2)
	{
		return (float) Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
	}
	
	private void Update_Game()
		{
		
		//for(Tank2 toDraw : allTanks)
		for(String key : TankHash.keySet())
		{
			TankInfoPacket Tank = TankHash.get(key);
			
			//make sure we're in the right ballpark.
			if(Tank.tankAngle > 360)
				Tank.tankAngle -= 360;
			if(Tank.tankAngle < 0)
				Tank.tankAngle += 360;
			
			if(Tank.dead)
				continue;
			
			if(Tank.TankCreatedAt < new Date().getTime()+5000)
				continue;
			
			
			for(String keyAgainst : TankHash.keySet())
			{
				if(keyAgainst.equals(key))
					continue; //ignore ourselves.
				else
				{
					
					TankInfoPacket TankAgainst = TankHash.get(keyAgainst);
					
					if(TankAgainst.tankAngle > 360)
						TankAgainst.tankAngle -= 360;
					if(TankAgainst.tankAngle < 0)
						TankAgainst.tankAngle += 360;
					
					if(TankAgainst.dead)
						continue;
					if(TankAgainst.TankCreatedAt < new Date().getTime()+5000)
						continue;
					if(dist(Tank.x,Tank.y,TankAgainst.x,TankAgainst.y) < 48) //arbitrary number, change it
					{
						//we are in collision range.
						System.out.println("TANK COLLISION!");
						
						//are we facing their side? ae, are we more than 60 degrees above or below their current angle?
						
						boolean killAgainst = false;
						
						//we need to compare their angles to figure this out.
						//if we're facing towards them, with a threshold of 60, and they ARENT facing us, they're dead.
						
						
						
						
						Tank.dead = true;
						TankAgainst.dead = true;
						
						TankHash.put(key, Tank);
						TankHash.put(keyAgainst, TankAgainst);
						
						graveLocs.add(new TankGraveLoc(Tank.x,Tank.y));
						
						deadKeys.add(key);
						deadKeys.add(keyAgainst);

						
					}//
				
				}
			}
			
			
			
		}
		
			//for every tank
		//iterate for every tank minus itself
		//get the dist
		//if the dist is less than 100, kill them both
		
			
		}
	
	
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		        Update();
		    }
		};		
}


