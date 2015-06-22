package mexica.network.server;

import java.nio.ByteBuffer;

import mexica.network.Lounge;
import mexica.network.RoomManager;

import com.nionetframework.common.ConnectionManager;
import com.nionetframework.common.NetworkThread;
import com.nionetframework.common.logger.NetworkLogger;
import com.nionetframework.server.Server;
import com.nionetframework.server.events.ConnectionCloseEvent;
import com.nionetframework.server.events.ConnectionNewEvent;
import com.nionetframework.server.events.ServerEventDispatcher;
import com.nionetframework.server.events.ServerEventListener;
import com.nionetframework.server.events.ServerEvents;

public class ServerExample implements ServerEventListener {

	private Server server;
	private RoomManager roommanager;

	public ServerExample() {
		this.roommanager = RoomManager.getDefault();
		roommanager.addLobby(Lounge.getDefault());
		
		initializeServer();
	}
	
	private void initializeServer() {
		NetworkLogger.setLogLevel(NetworkLogger.DEBUG);
		ServerEventDispatcher.registerListener(this);
		this.server = Server.getDefaultServer();
		server.start();
	}
	
	@ServerEvents
	public void onConnectionNewEvent(ConnectionNewEvent e) {
		
	}
	
	@ServerEvents
	public void onConnectionCloseEvent(ConnectionCloseEvent e) {
		
	}

}
