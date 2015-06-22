package mexica.network.server.serverprotocols;

import mexica.network.User;

import com.nionetframework.common.PacketInbound;
import com.nionetframework.common.PacketOutbound;

public class _Login_Protocol extends Protocol {

	@Override
	public PacketOutbound validate(PacketInbound packet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void engageFor(User u) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTerminationReason() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
