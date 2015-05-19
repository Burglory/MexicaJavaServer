package mexica.multiplayer.core;

public abstract class CommStandards {

	public static final class CommConnection {
		public static final String Token = "t";
		public static final String Answer = "a";
		public final static String Username = "u";
		public final static String Password = "p";
		public static final String Reason = "r";
	}

	public static abstract class CommChat {
		public final static String ChatDestination = "d";
		public final static String ChatMessage = "m";
		public final static String Sender = "s";
	}

	public static abstract class CommGameLobby {
		public final static String MaximumObservers = "mo";
		public final static String MaximumPlayers = "mp";
		public final static String GameLobbyName = "n";
		public final static String GameLobbyPassword = "p";
		public static final String Reason = "r";
		public static final String Status = "s";
		public static final String Answer = "a";
		public static final String GameLobbySettings = "ls";
		public static final String Admin = "ad";

	}

	public static abstract class CommServer {
		public static final String ServerVersion = "sv";
		public static final String OnlinePlayers = "ops";
		public static final String Lobbys = "l";
	}

	public static abstract class CommOnlineGame {

		public final static String OnlineGame = "og";
		public static final String Settings = "gs";
		public static final String Name = "n";

		public static abstract class CommOnlineGameAction {
			public static final String OnlineGameAction = "ga";
		}

		public static abstract class CommOnlinePlayer {

			public final static String Username = "u";

		}

	}

	public static String parseBoolean(boolean bool) {
		if (bool)
			return "true";
		return "false";
	}

	public static boolean parseStringBoolean(String string) {
		return string.equalsIgnoreCase("true");
	}

}
