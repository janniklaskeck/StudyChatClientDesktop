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

	private SocketClient client;

	@FXML
	public void initialize() {
		try {
			// client = new SocketClient(new
			// URI("ws://studychatserver.mybluemix.net"));
			client = new SocketClient(new URI("ws://127.0.0.1:5000"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		connectButton.setOnAction(event -> {

			client.connect();

		});
		sendButton.setOnAction(event -> {
			final String userName = userNameField.getText();
			client.send(buildUserJoinMessage(userName));
		});

	}

	private static String buildUserJoinMessage(final String userName) {
		final JsonObject jo = new JsonObject();
		jo.addProperty("version", 1);
		jo.addProperty("type", "USER_JOIN");

		final JsonObject joMsg = new JsonObject();
		joMsg.addProperty("userName", userName);

		jo.add("content", joMsg);
		return jo.toString();
	}

}
