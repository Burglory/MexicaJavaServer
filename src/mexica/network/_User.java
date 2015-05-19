package mexica.network;

import com.nionetframework.common.Connection;
import com.nionetframework.common._Connection;

public class _User extends User {

	private String username;
	private _Connection connection;
	private _NetworkGame onlinegame;
	private _Room onlinegamelobby;

	public _User(_Connection c, String username) {
		this.username = username;
		this.connection = c;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public NetworkGame getNetworkGame() {
		return this.onlinegame;
	}

	public void unsetOnlineGame() {
		this.onlinegame = null;
	}

	public void setOnlineGame(_NetworkGame o) {
		this.onlinegame = o;
	}

	@Override
	public Room getRoom() {
		return this.onlinegamelobby;
	}

	public void unsetOnlineGameLobby() {
		this.onlinegamelobby = null;
	}

	public void setOnlineGameLobby(_Room o) {
		this.onlinegamelobby = o;
	}

	@Override
	public boolean isSpectator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayer() {
		// TODO Auto-generated method stub
		return false;
	}

}
