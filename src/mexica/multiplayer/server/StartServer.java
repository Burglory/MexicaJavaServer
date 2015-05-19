package mexica.multiplayer.server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import mexica.multiplayer.server.utilities.ServerCleanup;
import mexica.multiplayer.server.utilities.ServerLogger.ServerLogLevel;

public class StartServer {

	public final static JPanel mainpanel = new JPanel();
	public final static JTextArea maintext = new JTextArea();

	public static void main(String[] args) {
		StartServer.initialize();
		int port = 12345;
		Server server = new Server(port, ServerLogLevel.DEBUG);
		server.getLogger().Log("Server Created", ServerLogLevel.DEBUG);
		Runtime.getRuntime().addShutdownHook(
				new Thread(new ServerCleanup(server)));
		server.Initialize();
	}

	public static void initialize() {
		maintext.setSize(100, 100);
		mainpanel.add(maintext);
		mainpanel.setSize(100, 100);
		JFrame f = new JFrame("Server");
		f.add(StartServer.mainpanel);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setLocationByPlatform(true);
		// ensures the frame is the minimum size it needs to be
		// in order display the components within it
		f.pack();
		// ensures the minimum size is enforced.
		f.setMinimumSize(f.getSize());
		f.setVisible(true);

	}
}
