package mexica.network;

import com.nionetframework.common.Connection;

public abstract class User {

	public abstract String getUsername();

	public abstract Connection getConnection();

	public abstract NetworkGame getNetworkGame();

	public abstract Room getRoom();

	public abstract boolean isSpectator();

	public abstract boolean isPlayer();

}
