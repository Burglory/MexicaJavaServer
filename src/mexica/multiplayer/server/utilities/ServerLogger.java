package mexica.multiplayer.server.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import mexica.multiplayer.server.StartServer;

public class ServerLogger {

	private final ServerLogLevel loglevel;

	public ServerLogger(ServerLogLevel loglevel) {
		this.loglevel = loglevel;
	}

	public void Log(String text, ServerLogLevel loglevel) {
		if (loglevel.getLevel() <= this.loglevel.getLevel()) {
			text = new SimpleDateFormat().format(new Date()) + ": "
					+ loglevel.toString() + ": " + text;
			Log(text);
		}
	}

	private void Log(String text) {
		StartServer.maintext.append(text + "\n");
	}

	public enum ServerLogLevel {
		ERROR(0), WARNING(1), INFO(2), DEBUG(3);

		private int level;

		private ServerLogLevel(int level) {
			this.level = level;
		}

		public int getLevel() {
			return this.level;
		}
	}

}
