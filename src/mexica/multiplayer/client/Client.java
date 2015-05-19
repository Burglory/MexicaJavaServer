package mexica.multiplayer.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import mexica.multiplayer.client.ClientLogger.ClientLogLevel;

class Client {

	private ClientLogger clientlogger;
	private ClientConnection clientconnection;
	private String host;
	private String port;

	public Client(String host, String port) {
		this.host = host;
		this.port = port;
		this.clientlogger = new ClientLogger(ClientLogLevel.DEBUG);

	}

	void Initialize() {
		try {
			// System.setProperty("javax.net.ssl.keyStore", "sharedkey");
			// System.setProperty("javax.net.ssl.keyStorePassword", "password");
			// Socket socket = SSLSocketFactory.getDefault().createSocket(host,
			// Integer.parseInt(port));
			Socket socket = new Socket(host, Integer.parseInt(port));
			this.clientconnection = new ClientConnection(this, socket);
			Thread connectionthread = new Thread(clientconnection);
			connectionthread.start();
		} catch (ConnectException e) {
			this.getLogger().Log(
					"Server refused connection at: " + host + ":" + port,
					ClientLogLevel.ERROR);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			System.out.println("Trying to log in...");
			if (this.clientconnection.LogIn("Frank", "testingserver123")) {
				System.out.println("Sent packet succesfully");
				break;
			} else {
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ClientLogger getLogger() {
		return this.clientlogger;
	}

}
