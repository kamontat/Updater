package main.java.com.kamontat.code.popup;

import main.java.com.kamontat.code.config.Configuration;
import main.java.com.kamontat.code.server.Updater;
import main.java.com.kamontat.code.utilities.DesktopAction;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 2:27 PM
 */
public abstract class DescriptionPopup implements Runnable {
	private static JDialog dialog;
	protected Updater update;
	
	public DescriptionPopup(Updater update) throws IOException {
		this.update = update;
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JEditorPane label = new JEditorPane(Configuration.INPUT_TYPE, update.getDescription());
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
				otherEvent(e.getInputEvent(), e.getSourceElement(), e.getURL(), e.getDescription());
			}
		});
		panel.add(label, BorderLayout.CENTER);
		JButton button = new JButton(Configuration.DOWNLOAD_BUTTON);
		button.addActionListener(e -> {
			downloadAction();
		});
		panel.add(button, BorderLayout.SOUTH);
		return panel;
	}
	
	protected void setTooltip(InputEvent event, String text) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(text);
	}
	
	protected void removeTooltip(InputEvent event) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(null);
	}
	
	protected void open(URL url) {
		DesktopAction.get().browse(url);
	}
	
	/**
	 * Need to implement
	 */
	protected void downloadAction() {
		// alert
		if (Configuration.HAS_ALERT) {
			int result = JOptionPane.showConfirmDialog(dialog, Configuration.ALERT_COMPLETE_MESSAGE, Configuration.ALERT_COMPLETE_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
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
	protected void click(InputEvent event, Element element, URL link, String description) {
		open(link);
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
	protected void placeholder(InputEvent event, Element element, URL link, String description) {
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
	protected void exitPlaceholder(InputEvent event, Element element, URL link, String description) {
		removeTooltip(event);
	}
	
	/**
	 * need to implement
	 *
	 * @param inputEvent
	 * 		input event
	 * @param sourceElement
	 * 		element
	 * @param url
	 * 		url link
	 * @param description
	 * 		description or link
	 */
	protected void otherEvent(InputEvent inputEvent, Element sourceElement, URL url, String description) {
	}
	
	protected void waiting() {
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
	protected void done() {
		dialog.setCursor(Cursor.getDefaultCursor());
	}
	
	@Override
	public void run() {
		try {
			dialog = new JDialog((Frame) null, update.getTitle() + ": " + update.getVersion());
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setContentPane(setPanel());
			dialog.pack();
			dialog.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
