package stud.mi.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

	private static final int MIN_WIDTH = 650;
	private static final int MIN_HEIGHT = 550;
	private static Stage rootstage;

	@Override
	public final void init() {
		Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> Platform.exit());
		Platform.setImplicitExit(false);
	}

	@Override
	public final void start(Stage primaryStage) {
		final Parent root = this.loadFxml();
		this.setupStage(root, primaryStage);
	}

	private Parent loadFxml() {
		try {
			return FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
			return null;
		}
	}

	private void setupStage(final Parent root, final Stage primaryStage) {
		setRootStage(primaryStage);
		primaryStage.setTitle("Student Chat Client - Desktop");
		primaryStage.setMinHeight(MIN_HEIGHT);
		primaryStage.setHeight(MIN_HEIGHT);

		primaryStage.setMinWidth(MIN_WIDTH);
		primaryStage.setWidth(MIN_WIDTH);

		final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(event -> Platform.exit());
		primaryStage.setOnHiding(event -> Platform.exit());
	}

	public static final synchronized Stage getRootStage() {
		return rootstage;
	}

	private static final synchronized void setRootStage(final Stage newRootStage) {
		rootstage = newRootStage;
	}

}
