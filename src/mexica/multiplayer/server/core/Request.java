package mexica.multiplayer.server.core;

import mexica.core.utilities.DataString;
import mexica.multiplayer.core.CommStandards;
import mexica.multiplayer.core.Packet;

public class Request {

	private final Packet requestpacket;
	private final User requester;
	private int necessarycount;
	private int currentcount;
	private Packet[] responses;

	public Request(Packet requestpacket, User requester, int necessarycount) {
		this.requestpacket = requestpacket;
		this.requester = requester;
		this.currentcount = 0;
		this.necessarycount = necessarycount;
		this.responses = new Packet[necessarycount];
	}

	public boolean evaluate() {
		int truecount = 0;
		int neededcount = this.getNecessaryCount();
		for (Packet responsepacket : this.getResponses()) {
			if (CommStandards.parseStringBoolean(new DataString(responsepacket
					.getData()).get("a"))) {
				truecount++;
			}
		}
		return (neededcount == truecount);
	}

	public Packet getRequestpacket() {
		return requestpacket;
	}

	public User getRequester() {
		return requester;
	}

	public void addResponse(Packet response) {
		this.responses[currentcount] = response;
		this.currentcount++;
	}

	public Packet[] getResponses() {
		return this.responses;
	}

	public boolean hasResponse() {
		return this.currentcount > 0;
	}

	public boolean hasResponse(int amount) {
		return this.currentcount >= amount;
	}

	public boolean hasEnoughResponses() {
		return this.currentcount == this.necessarycount;
	}

	public int getNecessaryCount() {
		return this.necessarycount;
	}

	public int getCurrentCount() {
		return this.currentcount;
	}
}
