package mexica.multiplayer.server.utilities.packetprocessors;

import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.core.PacketType;
import mexica.multiplayer.server.core.Connection;
import mexica.multiplayer.server.core.User;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;
import mexica.multiplayer.server.utilities.UserDatabase;

public abstract class ConnectionPacketProcessor {

	private static boolean validates() {
		// TODO: Database kind of thing.
		return true;
	}

	public static void process(Connection connection, Packet p) {
		DataString data = new DataString(p.getData());

		String username = data.get(CommStandards.CommConnection.Username);
		String hashedpassword = data.get(CommStandards.CommConnection.Password);

		switch (p.getType()) {
		case PacketType.Client.Connection.Authentication.Login.Request:
			connection
					.getServer()
					.getLogger()
					.Log("(PacketProcessor) Received auth packet from :"
							+ connection.getSocket().getInetAddress()
							+ " for username: " + username,
							ServerLogLevel.DEBUG);

			if (UserDatabase.Contains(username, hashedpassword,
					connection.getToken())) {
				connection
						.getServer()
						.getLogger()
						.Log("(PacketProcessor) Auth of " + username
								+ " is succesful.", ServerLogLevel.DEBUG);
				;
				connection
						.getServer()
						.getConnectionManager()
						.setUserConnection(
								connection,
								new User(connection, username, UserDatabase
										.getUUID(username)));
				connection
						.send(PacketType.Server.Connection.Authentication.Login
								.generateResponsePacket(true));
				connection.send(PacketType.Server.Connection.Authentication
						.generateTokenPacket(connection.renewToken()));
				connection
						.getServer()
						.getConnectionManager()
						.broadcast(
								PacketType.Server.Connection
										.generateJoinPacket(username));
			} else {
				connection
						.send(PacketType.Server.Connection.Authentication.Login
								.generateResponsePacket(false));
				connection.send(PacketType.Server.Connection.Authentication
						.generateTokenPacket(connection.renewToken()));
				connection
						.getServer()
						.getLogger()
						.Log("Authentication failed. Sending new token: "
								+ connection.getToken(), ServerLogLevel.DEBUG);

			}
		case PacketType.Client.Connection.Authentication.Register.Request:
			if (UserDatabase.containsUsername(username)) {
				connection
						.send(PacketType.Server.Connection.Authentication.Register
								.generateResponsePacket(false));
			} else {
				connection
						.send(PacketType.Server.Connection.Authentication.Register
								.generateResponsePacket(true));
				// TODO: encryption for password?
				UserDatabase.add(username, hashedpassword);
			}
		}

	}

}
