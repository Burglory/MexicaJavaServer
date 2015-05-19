package mexica.multiplayer.server.core;

import java.util.ArrayList;
import java.util.HashMap;

import mexica.core.Game;
import mexica.core.utilities.DataString;
import mexica.core.utilities.GameColors;
import mexica.multiplayer.core.Packet;
import mexica.multiplayer.server.utilities.SecurityUtilities;
import mexica.multiplayer.server.utilities.packetprocessors.PlayPacketProcessor;

public class OnlineGame {

	public enum OnlineGameStatus {
		STARTED("SA"), PAUSED("PA"), FINISHED("FI"), STOPPED("ST");

		private String sh;

		private OnlineGameStatus(String sh) {
			this.sh = sh;
		}

		public String getShort() {
			return this.sh;
		}

		public static OnlineGameStatus getOnlineGameStatus(String sh) {
			for (OnlineGameStatus ogs : OnlineGameStatus.values()) {
				if (ogs.getShort().equals(sh))
					return ogs;
			}
			return null;
		}
	}

	private ArrayList<User> players;
	private HashMap<String, Connection> playerconnections;
	private String uuid;
	private User host;
	private DataString gamesettings;
	private OnlineGameStatus onlinegamestatus;
	private boolean servergame;
	private Game game;
	private ArrayList<Request> requests;

	/**
	 * Object representing a Online Game. {@link boolean} servergame specificies
	 * if there is a {@link Game} object internally.
	 */
	public OnlineGame(ArrayList<User> players, User host, boolean servergame) {
		this.players = players;
		this.host = host;
		this.playerconnections = new HashMap<String, Connection>();
		this.playerconnections.put(host.getUsername(), host.getConnection());
		this.requests = new ArrayList<Request>();
		this.uuid = SecurityUtilities.generateRandomToken();
		this.servergame = servergame;
		if (this.servergame) {
			this.initializeGame();
		}
	}

	/** Process a {@link Packet} for this OnlineGame. */
	public void processPlayPacket(Connection connection, Packet p) {
		PlayPacketProcessor.process(requests, connection, this, p);
	}

	/** Creates a {@link Game} internally. */
	private void initializeGame() {
		this.game = new Game();
		for (int i = 0; i < players.size(); i++) {
			game.getGameManager().newHumanPlayer(players.get(i).getUsername(),
					GameColors.getGameColor(i));
		}
	}

	public void setGameSettings(DataString gamesettings) {
		this.gamesettings = gamesettings;
	}

	public DataString getGameSettings() {
		return this.gamesettings;
	}

	/**
	 * Returns the token of this onlinegame. Used for Encryption of savegames
	 * and identification internally.
	 */
	public String getUUID() {
		return this.uuid;
	}

	/** Returns a {@link User} based on its username. */
	public User getOnlinePlayer(String username) {
		for (User u : this.getOnlinePlayers()) {
			if (u.getUsername().equals(username))
				return u;
		}
		return null;
	}

	public ArrayList<User> getOnlinePlayers() {
		return this.players;
	}

	public Connection getConnection(String playername) {
		return this.playerconnections.get(playername);
	}

	/**
	 * Sends Packet p to all users in this OnlineGame except the User specified
	 * by String exceptuser.
	 */
	public void relay(Packet p, String exceptuser) {
		for (String player : this.playerconnections.keySet()) {
			if (!player.equals(exceptuser)) {
				this.playerconnections.get(player).send(p);
			}
		}
	}

	/**
	 * Sends Packet p to all users in this OnlineGame except the User specified
	 * by user.
	 */
	public void relay(Packet p, User user) {
		this.relay(p, user.getUsername());
	}

	/** Sends Packet p to all users in this OnlineGame. */
	public void broadcast(Packet p) {
		for (Connection connection : this.playerconnections.values()) {
			connection.send(p);
		}
	}

	/**
	 * Gets the String of this OnlineGame. Formatted as: p{players}gst{boolean}
	 */
	public String getString() {
		return "p{"
				+ this.getOnlinePlayers().toString().replace("[", "")
						.replace("]", "").replace(" ", "") + "}gst{"
				+ this.getOnlineGameStatus().getShort() + "}";
	}

	/**
	 * Returns true if the OnlineGameStatus of this OnlineGame is STARTED or
	 * PAUSED.
	 */
	public boolean isStarted() {
		return this.getOnlineGameStatus().equals(OnlineGameStatus.STARTED)
				|| this.getOnlineGameStatus().equals(OnlineGameStatus.PAUSED);
	}

	/**
	 * Should only be used if the User has been previously disconnected from the
	 * game and returns. Currently not supported.
	 */
	@Deprecated
	public void addOnlinePlayer(User u) {
		this.players.add(u);
		this.playerconnections.put(u.getUsername(), u.getConnection());
		u.setOnlineGame(this);
	}

	/** Should only be used when User is kicked or disconnected from Server. */
	public void removeOnlinePlayer(User u) {
		this.players.remove(u);
		this.playerconnections.remove(u.getUsername());
		u.setOnlineGame(null);
	}

	public User getHost() {
		return this.host;
	}

	public OnlineGameStatus getOnlineGameStatus() {
		return this.onlinegamestatus;
	}

	public void setStatus(OnlineGameStatus onlineGameStatus) {
		this.onlinegamestatus = onlineGameStatus;
	}

	/**
	 * Returns whether GameAction packets are checked on the Server or deligated
	 * to a _Client.
	 */
	public boolean isServergame() {
		return servergame;
	}

	/**
	 * Set whether GameAction packets are checked on the Server or deligated to
	 * a _Client.
	 */
	public void setServergame(boolean servergame) {
		this.servergame = servergame;
	}

	/** Returns the Game Object for this OnlineGame. */
	public Game getGame() {
		return game;
	}

}
