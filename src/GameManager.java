import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import serverConnection.ClientToServerPacket;

public class GameManager extends Thread
{
	final static int desiredTickRate = 30; //how many ticks per second are we aiming for?
	
	static long lastTick; //when was the last tick? used for determining deltaTime
	
	static long GameStarted;
	
	static long GameEndTime;
	
	static HashMap<String,String> IPtoID = new HashMap<String,String>();
	static HashMap<String,String> IDtoTank = new HashMap<String,String>();
	
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
			ScheduledExecutorService TickExecutor = Executors.newScheduledThreadPool(1);
			TickExecutor.scheduleAtFixedRate(TankUpdateRunnable, 0, 1000/desiredTickRate, TimeUnit.MILLISECONDS);
			//tick @ whatever rate we decide on - shouldnt matter for the client. their update is independant.
			
	}
	//called every tick
	private void Update()
	{
			Update_Lobby();
			Update_Game();
		
			CachePacket();
			lastTick = new Date().getTime();
	}
	
	private void Update_Game()
		{
			//doesn't actually do anything until we get projectiles going.
			// TODO Auto-generated method stub
			
		}

	private void CachePacket()
		{
			// TODO Auto-generated method stub
			
		}

	private void Update_Lobby()
		{
			//
//
			//
			// How many people are connected? - do NOT send the fucking IPs over
			//	current gamemode?
			//
			//
			//
			//
			//
			//
			//
			
		}
	
	
	
	
	
	
	
	

	private boolean CanWeStart()
	{
			//we need at least 2 staffed tank to run!
			return false;
	}
	
	
	Runnable TankUpdateRunnable = new Runnable() {
		    public void run() {
		        Update();
		    }
		};		
}


