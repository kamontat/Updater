package com.kamontat.code.gui;

import com.kamontat.code.config.Configuration;
import com.kamontat.code.model.Updater;
import com.kamontat.code.utilities.DesktopAction;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.net.URL;

/**
 * The popup that contains update description and downloader link.
 * <p>
 * Need to implement method: <br>
 * <ol>
 * <li> {@link #actionPerformed(ActionEvent)} - when user click download/update button</li>
 * </ol>
 * Might to implement method: <br>
 * <ol>
 * <li>{@link #click(InputEvent, Element, URL, String)}</li>
 * <li>{@link #placeholder(InputEvent, Element, URL, String)}</li>
 * <li>{@link #exitPlaceholder(InputEvent, Element, URL, String)}</li>
 * <li>{@link #otherEvent(InputEvent, Element, URL, String)}</li>
 * </ol>
 * Helper Method: <br>
 * <ol>
 * <li>{@link #waiting()}</li>
 * <li>{@link #done()}</li>
 * <li>{@link #setTooltip(InputEvent, String)}</li>
 * <li>{@link #removeTooltip(InputEvent)}</li>
 * <li>{@link #open(URL)}</li>
 * </ol>
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 2:27 PM
 */
public abstract class ReleasePopup extends AbstractAction implements Runnable {
	private static JDialog dialog;
	protected Updater update;
	
	/**
	 * Constructor to create popup <br>
	 *
	 * @param update
	 * 		The updater
	 */
	public ReleasePopup(Updater update) {
		this.update = update;
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		// north
		panel.add(new JLabel(String.format("version: %s -> %s", Updater.getCurrentVersion(), Updater.getRemoteVersion()), SwingConstants.CENTER), BorderLayout.NORTH);
		// center
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
		// south
		JButton button = new JButton(Configuration.DOWNLOAD_BUTTON);
		button.addActionListener(this);
		panel.add(button, BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	 * the download button been click
	 *
	 * @param e
	 * 		action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// alert
		if (Configuration.HAS_ALERT) {
			int result = JOptionPane.showConfirmDialog(null, Configuration.ALERT_COMPLETE_MESSAGE, Configuration.ALERT_COMPLETE_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
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
		dialog = new JDialog((Frame) null, "Update Page");
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setContentPane(setPanel());
		dialog.pack();
		dialog.setVisible(true);
	}
}
