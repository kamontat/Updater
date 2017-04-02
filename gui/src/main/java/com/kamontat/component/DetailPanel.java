package com.kamontat.component;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 26/Mar/2017 - 6:07 PM
 */
public class DetailPanel extends Application {
	public Scene initScene() {
		String html = "<html><body><h1>Hello world!</h1><br><code>print(\"say hello\");</code></body></html>";
		String md = "How *can* **i** do `this`";
		
		// HTMLEditor editor = new HTMLEditor();
		// editor.setHtmlText();
		WebView v = new WebView();
		WebEngine engine = v.getEngine();
		engine.loadContent(md, "text/markdown");
		
		StackPane pane = new StackPane(v);
		return new Scene(pane);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(initScene());
		primaryStage.show();
	}
}
