package mexica.multiplayer.server.utilities.packetprocessors;

import java.util.ArrayList;

import mexica.core.utilities.DataStandards.DataGame.DataGameAction;
import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.core.Packets;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.OnlineGame;
import mexica.multiplayer.server.core.Request;
import mexica.multiplayer.server.core.User;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public abstract class PlayPacketProcessor {

	private synchronized static Request getRequestFor(
			ArrayList<Request> requests, Packet responsepacket) {
		Request request = null;
		for (Request r : requests) {
			if (Packets.isReponse(r.getRequestpacket(), responsepacket)) {
				request = r;
			}
		}
		return request;
	}

	private synchronized static void addRequest(ArrayList<Request> requests,
			Request request) {
		requests.add(request);
	}

	private static void Do(ArrayList<Request> requests, User sender,
			User actor, OnlineGame onlinegame, String stringplayeraction,
			Connection connection, Packet p) {
		if (sender.equals(actor)) {
			addRequest(requests, new Request(p, actor, onlinegame
					.getOnlinePlayers().size() - 1));
			Packet packet = PacketType.Server.Play.GameAction.Verification
					.generateRequestPacket(stringplayeraction);
			onlinegame.relay(packet, actor.getUsername());
		} else {
			connection
					.getServer()
					.getLogger()
					.Log("User: " + sender.getUsername()
							+ " tried to impersonate a playeraction from: "
							+ actor.getUsername(), ServerLogLevel.WARNING);
		}
	}

	private static void Undo(User sender, User actor, String username,
			OnlineGame onlinegame, String playeraction) {
		if (sender.equals(actor)) {
			if (sender.getUsername().equals(username)) {
				onlinegame.broadcast(PacketType.Server.Play.GameAction
						.generateUndoPacket(playeraction));
			}
		}
	}

	private static void clientResponse(ArrayList<Request> requests,
			User sender, User actor, String username, Packet p,
			OnlineGame onlinegame, String playeraction, Connection connection) {
		Request request = getRequestFor(requests, p);
		if (request != null) {
			if (!sender.equals(actor)) {
				request.addResponse(p);
				if (request.hasEnoughResponses()) {
					if (request.evaluate()) {
						actor.getConnection().send(
								PacketType.Server.Play.GameAction.Verification
										.generateResponsePacket(true));
						onlinegame.broadcast(PacketType.Server.Play.GameAction
								.generateDoPacket(playeraction));
					} else {
						actor.getConnection().send(
								PacketType.Server.Play.GameAction.Verification
										.generateResponsePacket(false));
					}
				} else {
					connection
							.getServer()
							.getLogger()
							.Log("Still waiting for "
									+ (request.getNecessaryCount() - request.getCurrentCount())
									+ "responses.", ServerLogLevel.DEBUG);
				}
			} else {
				connection
						.getServer()
						.getLogger()
						.Log("User: " + sender.getUsername()
								+ " tried to respond to its own playeraction!",
								ServerLogLevel.WARNING);
			}
		} else {
			connection
					.getServer()
					.getLogger()
					.Log("Invalid reponse packet (no request) from: "
							+ sender.getUsername(), ServerLogLevel.WARNING);
		}
	}

	public static void process(ArrayList<Request> requests,
			Connection connection, OnlineGame onlinegame, Packet p) {
		DataString data = new DataString(p.getData());
		DataString datagameaction = data.select(DataGameAction.GameAction);
		String gameaction = datagameaction.getString();
		User sender = connection.getUser();
		String username = datagameaction
				.get(CommStandards.CommOnlineGame.CommOnlinePlayer.Username);
		User actor = onlinegame.getOnlinePlayer(username);
		switch (p.getType()) {
		case PacketType.Client.Play.GameAction.Do:
			Do(requests, sender, actor, onlinegame, gameaction, connection, p);
			break;
		case PacketType.Client.Play.GameAction.Undo:
			Undo(sender, actor, username, onlinegame, gameaction);
			break;
		case PacketType.Client.Play.GameAction.Verification.Response:
			clientResponse(requests, sender, actor, username, p, onlinegame,
					gameaction, connection);
			break;
		}

	}

}
