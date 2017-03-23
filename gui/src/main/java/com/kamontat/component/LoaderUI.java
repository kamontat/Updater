package com.kamontat.component;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 23/Mar/2017 - 10:13 PM
 */
public class LoaderUI extends Application {
	public LoaderUI() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Test page");
		primaryStage.setScene(new Scene(new StackPane(), 500, 500));
		primaryStage.show();
	}
	
	private Scene initScene() {
		// Scene s = new Scene();
		return null;
	}
	
	
	public static void main(String[] args) {
		LoaderUI.launch(args);
	}
}
