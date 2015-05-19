package mexica.network.events;

import mexica.network.User;

public abstract class PlayerNetworkEvent extends NetworkEvent{

	public static final String EVENT_NAME = "p";
	public static final String USER_FIELD = "u";
	private User user;

	public PlayerNetworkEvent(User u) {
		super(u.getConnection().getConnectionManager().getServer());
		this.user = u;
	}
	
	public User getUser() {
		return this.user;
	}
	
}
