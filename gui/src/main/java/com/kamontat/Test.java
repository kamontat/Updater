package com.kamontat;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 23/Mar/2017 - 7:20 PM
 */
public class Test extends Application {
	private StackPane root;
	
	public Test() {
		root = new StackPane();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Test page");
		Button b = new Button("HW");
		b.setOnAction(event -> System.out.println("Hello world"));
		
		root.getChildren().add(b);
		
		primaryStage.setScene(initScene());
		primaryStage.show();
	}
	
	/**
	 * export fx to swing
	 *
	 * @return {@link JFXPanel} - for set in {@link JFrame#setContentPane(Container)}
	 */
	public JFXPanel get() {
		JFXPanel panel = new JFXPanel();
		panel.setScene(initScene());
		return panel;
	}
	
	private static Scene initScene() {
		Group root = new Group();
		Scene scene = new Scene(root, javafx.scene.paint.Color.ALICEBLUE);
		Text text = new Text();
		text.setX(40);
		text.setY(100);
		text.setFont(new Font(25));
		text.setText("Welcome JavaFX!");
		root.getChildren().add(text);
		return (scene);
		
	}
}
