package stud.mi.gui;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import stud.mi.SocketClient;

public class MainController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextArea sendArea;

    @FXML
    private TextField userNameField;

    @FXML
    private Button sendButton;

    @FXML
    private Button connectButton;

    @FXML
    private Button joinChannelButton;

    private static SocketClient client;

    @FXML
    public void initialize() {
        try {
            // client = new SocketClient(new
            // URI("ws://studychatserver.mybluemix.net"));
            client = new SocketClient(new URI("ws://127.0.0.1:5000"), this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        connectButton.setOnAction(event -> {
            boolean success = false;
            try {
                success = client.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            if (success) {
                final String userName = userNameField.getText();
                client.send(buildUserJoinMessage(userName));
            } else {
                System.err.println("ERROR");
            }
        });
        sendButton.setOnAction(event -> {
            final String message = sendArea.getText();
            client.send(buildSendMessage(message));
            sendArea.clear();
        });

        joinChannelButton.setOnAction(event -> {
            client.send(buildChannelJoinMessage("default"));
        });

    }

    private String buildSendMessage(final String message) {
        final JsonObject jo = new JsonObject();
        jo.addProperty("version", 1);
        jo.addProperty("type", MessageType.CHANNEL_MESSAGE);

        final JsonObject joMsg = new JsonObject();
        joMsg.addProperty("userID", client.userID);
        joMsg.addProperty("message", message);

        jo.add("content", joMsg);
        return jo.toString();
    }

    private String buildChannelJoinMessage(final String channelName) {
        final JsonObject jo = new JsonObject();
        jo.addProperty("version", 1);
        jo.addProperty("type", MessageType.CHANNEL_JOIN);

        final JsonObject joMsg = new JsonObject();
        joMsg.addProperty("userID", client.userID);
        joMsg.addProperty("channelName", channelName);

        jo.add("content", joMsg);
        return jo.toString();
    }

    private String buildUserJoinMessage(final String userName) {
        final JsonObject jo = new JsonObject();
        jo.addProperty("version", 1);
        jo.addProperty("type", MessageType.USER_JOIN);

        final JsonObject joMsg = new JsonObject();
        joMsg.addProperty("userName", userName);

        jo.add("content", joMsg);
        return jo.toString();
    }

    public void addMessage(final String userName, final String message) {
        final String msg = String.format("%s: %s%s", userName, message, System.lineSeparator());
        this.chatArea.appendText(msg);
    }

    public static void closeConnection() {
        try {
            client.closeBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
