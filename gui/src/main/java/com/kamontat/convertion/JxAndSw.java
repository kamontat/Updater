package com.kamontat.convertion;

import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javax.swing.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 04/Apr/2017 - 9:25 PM
 */
public class JxAndSw {
	public static JFXPanel toSwing(Scene s) {
		JFXPanel panel = new JFXPanel();
		panel.setScene(s);
		return panel;
	}
	
	public static Scene toJavaFX(JPanel panel) {
		SwingNode n = new SwingNode();
		n.setContent(panel);
		StackPane p = new StackPane(n);
		return new Scene(p);
	}
}
