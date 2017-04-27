package com.kamontat.component;

import com.kamontat.convert.Converter;
import com.kamontat.exception.ConversionException;
import com.kamontat.utilities.DesktopUtil;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.InputEvent;
import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 26/Mar/2017 - 6:07 PM
 */
public class DetailPanel extends JPanel {
	private String htmlText;
	
	// accept type
	public enum TextType {
		HTML,
		MARKDOWN
	}
	
	public DetailPanel(String text, TextType type) {
		try {
			if (type == TextType.MARKDOWN)
				this.htmlText = Converter.by(Converter.Type.MD2HTML).convert(text).toString();
			else this.htmlText = text;
		} catch (ConversionException e) {
			e.printStackTrace();
			this.htmlText = "";
		}
	}
	
	public JPanel init() {
		JEditorPane label = new JEditorPane("text/html", htmlText);
		label.setEditable(false);
		label.setOpaque(false);
		label.addHyperlinkListener(e -> {
			// hold
			if (e.getEventType().equals(HyperlinkEvent.EventType.ENTERED)) {
				placeholder(e.getInputEvent(), e.getSourceElement(), e.getURL(), e.getDescription());
				// out hold
			} else if (e.getEventType().equals(HyperlinkEvent.EventType.EXITED)) {
				exitPlaceholder(e.getInputEvent(), e.getSourceElement(), e.getURL(), e.getDescription());
				// click
			} else if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
				click(e.getInputEvent(), e.getSourceElement(), e.getURL(), e.getDescription());
			} else {
				// otherEvent(e.getInputEvent(), e.getSourceElement(), e.getURL(), e.getDescription());
			}
		});
		add(label, BorderLayout.CENTER);
		return this;
	}
	
	/**
	 * Need to implement
	 *
	 * @param event
	 * 		input event
	 * @param element
	 * 		element
	 * @param link
	 * 		url link
	 * @param description
	 * 		description or link
	 */
	private void click(InputEvent event, Element element, URL link, String description) {
		DesktopUtil.get().browse(link);
	}
	
	/**
	 * Need to implement
	 *
	 * @param event
	 * 		input event
	 * @param element
	 * 		element
	 * @param link
	 * 		url link
	 * @param description
	 * 		description or link
	 */
	private void placeholder(InputEvent event, Element element, URL link, String description) {
		setTooltip(event, description);
	}
	
	/**
	 * Need to implement
	 *
	 * @param event
	 * 		input event
	 * @param element
	 * 		element
	 * @param link
	 * 		url link
	 * @param description
	 * 		description or link
	 */
	private void exitPlaceholder(InputEvent event, Element element, URL link, String description) {
		removeTooltip(event);
	}
	
	private void setTooltip(InputEvent event, String text) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(text);
	}
	
	private void removeTooltip(InputEvent event) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(null);
	}
}
