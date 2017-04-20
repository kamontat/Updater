package com.kamontat;

import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.DesktopUtil;

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
 * <li>{@link #waited()}</li>
 * <li>{@link #done()}</li>
 * <li>{@link #setTooltip(InputEvent, String)}</li>
 * <li>{@link #removeTooltip(InputEvent)}</li>
 * </ol>
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 2:27 PM
 */
public class ReleasePopup extends AbstractAction implements Runnable {
	private static JDialog dialog;
	protected UpdaterFactory update;
	
	/**
	 * Constructor to create popup <br>
	 *
	 * @param update
	 * 		The updater
	 */
	public ReleasePopup(Updatable update) {
		this.update = UpdaterFactory.setUpdater(update);
	}
	
	public ReleasePopup(UpdaterFactory update) {
		this.update = update;
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		// north
		panel.add(new JLabel(String.format("version: %s -> %s", update.getCurrentVersion(), update.getRemoteVersion()), SwingConstants.CENTER), BorderLayout.NORTH);
		// center
		JEditorPane label = new JEditorPane("text/html", update.getDescription());
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
		JButton button = new JButton("update!");
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
		waited();
		String s = "";
		try {
			s = update.download(null);
		} catch (UpdateException e1) {
			s = e1.getMessage();
		}
		done();
		JOptionPane.showMessageDialog(null, s, "Message!", JOptionPane.PLAIN_MESSAGE);
	}
	
	protected void setTooltip(InputEvent event, String text) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(text);
	}
	
	protected void removeTooltip(InputEvent event) {
		JEditorPane.class.cast(event.getComponent()).setToolTipText(null);
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
	
	private void waited() {
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}
	
	private void done() {
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