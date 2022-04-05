package serverConnection;

public class ServerToClientPacket
{
	String packetType = "lobby";
	String packetInfo = "";
}
//This is simply another holder class for the client/server communication protocol.
//there's no explicit reason that it's split into client->server server-> client. anymore, at least.
//doesn't hurt anyone though, even if it's pretty much just a duplicate class - more explicit what it does, I guess.