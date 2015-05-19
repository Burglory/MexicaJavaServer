package mexica.network.events;

import com.nionetframework.server.Server;

public abstract class CancellableNetworkEvent extends NetworkEvent {

	private boolean cancelled;
	private boolean cancellable;

	public CancellableNetworkEvent(Server server) {
		super(server);
		this.cancelled = false;
		this.cancellable = true;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		if(!isCancellable()) throw new RuntimeException("Not allowed to change Cancel of NetworkEvent!");
		this.cancelled = cancelled;
	}
	
	public void setCancellabe(boolean cancellable) {
		this.cancellable = cancellable;
	}
	
	public boolean isCancellable() {
		return this.cancellable;
	}
	
}
