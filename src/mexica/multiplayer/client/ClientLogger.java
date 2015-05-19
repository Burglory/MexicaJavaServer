package mexica.multiplayer.client;

import java.text.SimpleDateFormat;
import java.util.Date;

class ClientLogger {

	private final ClientLogLevel loglevel;

	ClientLogger(ClientLogLevel loglevel) {
		this.loglevel = loglevel;
	}

	public void Log(String text, ClientLogLevel loglevel) {
		if (loglevel.getLevel() <= this.loglevel.getLevel()) {
			text = new SimpleDateFormat().format(new Date()) + ": "
					+ loglevel.toString() + ": " + text;
			Log(text);
		}
	}

	private void Log(String text) {
		StartClient.maintext.append(text + "\n");
	}

	public enum ClientLogLevel {
		ERROR(0), INFO(1), DEBUG(2);

		private int level;

		private ClientLogLevel(int level) {
			this.level = level;
		}

		public int getLevel() {
			return this.level;
		}
	}

}
