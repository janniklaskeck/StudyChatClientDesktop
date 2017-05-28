package stud.mi;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stud.mi.gui.MainController;
import stud.mi.gui.Message;
import stud.mi.gui.MessageType;

public class SocketClient extends WebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);

    public long userID = -1L;
    public String channel = "";
    private MainController controller;

    public SocketClient(final URI serverURI, final MainController controller) {
        super(serverURI);
        this.controller = controller;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        LOGGER.debug("Open Connection");

    }

    @Override
    public void onMessage(String message) {
        LOGGER.debug("Received Message: {}", message);
        parseMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOGGER.debug("Close Connection");

    }

    @Override
    public void onError(Exception ex) {
        LOGGER.error("Error", ex);

    }

    private void parseMessage(final String message) {
        final Message msg = new Message(message);
        switch (msg.getType()) {
        case MessageType.USER_JOIN:
            if (msg.getUserID() > 0) {
                this.userID = msg.getUserID();
            }
            break;
        case MessageType.ACK_CHANNEL_JOIN:
            this.channel = msg.getChannelName();
            break;
        case MessageType.CHANNEL_MESSAGE:
            this.controller.addMessage(msg.getUserName(), msg.getMessage());
            break;
        default:
            System.out.println("Message Type unknown: " + msg.getType());
        }
    }

}
