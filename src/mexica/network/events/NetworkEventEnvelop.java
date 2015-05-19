package mexica.network.events;

import java.util.Collection;

import com.nionetframework.common.Connection;
import com.nionetframework.common.Packet;

public class NetworkEventEnvelop {

	private final NetworkEvent networkevent;
	private final Collection<Connection> to;

	public NetworkEventEnvelop(NetworkEvent e, Collection<Connection> to) {
		this.networkevent = e;
				this.to = to;
	}
	
	private void send() {

	}
	
	public NetworkEvent getNetworkEvent() {
		return this.networkevent;
	}
	
	public Collection<Connection> getTo() {
		return this.to;
	}
	
}
