package serverConnection;

import java.util.ArrayList;

import gameObjects.GameObject;
import gameObjects.Projectile;
import gameObjects.Tank;

public class ServerToClientPacket
{
String GameState;
String LobbyMessage;

int Teams = 0; //how many teams? Lobby thing
int TanksAlive = 0; //in game thing

int youControl; //tank the client controls

ArrayList<Tank> allTanks = new ArrayList<Tank>();
ArrayList<Projectile> allProjectiles= new ArrayList<Projectile>();
ArrayList<GameObject> allMapObjects = new ArrayList<GameObject>();
//arraylist of tanks - null if we're still in lobby! ditto for everything below
//arraylist of projectiles
//arraylist of map objects


public String getJson() //convert everything to json
{
		return "he";
}
}
