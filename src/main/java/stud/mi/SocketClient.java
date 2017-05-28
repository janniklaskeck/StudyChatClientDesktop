package stud.mi;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClient extends WebSocketClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);

	public SocketClient(URI serverURI) {
		super(serverURI);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		LOGGER.debug("Open Connection");

	}

	@Override
	public void onMessage(String message) {
		LOGGER.debug("Received Message: {}", message);

	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		LOGGER.debug("Close Connection");

	}

	@Override
	public void onError(Exception ex) {
		LOGGER.error("Error", ex);

	}

}
