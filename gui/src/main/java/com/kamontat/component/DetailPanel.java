package com.kamontat.component;

import com.kamontat.constance.ContentType;
import com.kamontat.constance.Type;
import com.kamontat.convert.Converter;
import com.kamontat.exception.ConversionException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.*;

import static com.kamontat.component.DetailPanel.TextType.H;
import static com.kamontat.component.DetailPanel.TextType.HTML;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 26/Mar/2017 - 6:07 PM
 */
public class DetailPanel extends Application {
	private static final int SIZE = 2;
	private String htmlText;
	
	// accept type
	enum TextType {
		H,
		HTML,
		M,
		MD,
		MARKDOWN
	}
	
	private void initApp() {
		if (checkParamSize()) {
			TextType t;
			if ((t = getType()) != null) {
				if (t == H || t == HTML) htmlText = getString();
				else try {
					htmlText = Converter.by(Converter.Type.MD2HTML).convert(getString()).toString();
				} catch (ConversionException e) {
					throwException(new IllegalArgumentException(e));
				}
			} else {
				throwException(new IllegalArgumentException("Invalid input type"));
			}
		} else {
			throwException(new IllegalArgumentException("Invalid input size"));
		}
	}
	
	private Scene initScene() {
		
		
		// showing readme
		WebView v = new WebView();
		WebEngine engine = v.getEngine();
		engine.loadContent(htmlText, ContentType.get(Type.TEXT, ContentType.HTML));
		
		StackPane pane = new StackPane(v);
		return new Scene(pane);
	}
	
	@Override
	public void init() throws Exception {
		initApp();
		super.init();
	}
	
	private String getParam(int index) {
		return getParameters().getUnnamed().get(index);
	}
	
	private boolean checkParamSize() {
		return getParameters().getUnnamed().size() == SIZE;
	}
	
	private TextType getType() {
		for (TextType t : TextType.values()) {
			if (getParam(0).toLowerCase(Locale.ENGLISH).contains(t.name().toLowerCase(Locale.ENGLISH))) return t;
		}
		return null;
	}
	
	private String getString() {
		return getParam(1);
	}
	
	private void throwException(RuntimeException e) {
		throw e != null ? e: new RuntimeException("Error Occurred");
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(initScene());
		primaryStage.show();
	}
	
	public static Scene setHtml(String htmlText) {
		DetailPanel p = new DetailPanel();
		p.htmlText = htmlText;
		return p.initScene();
	}
	
	public static void main(String[] args) {
		DetailPanel.launch();
	}
}
