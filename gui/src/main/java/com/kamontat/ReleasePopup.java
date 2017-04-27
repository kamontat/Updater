package com.kamontat;

import com.kamontat.component.DetailPanel;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.DesktopUtil;

import javax.swing.*;
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
	public ReleasePopup(Updatable update) throws UpdateException {
		this(UpdaterFactory.setUpdater(update));
	}
	
	public ReleasePopup(UpdaterFactory update) throws UpdateException {
		this.update = update;
		update.checkRelease();
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		// north
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(String.format("current: %-6s", update.getCurrentVersion()), SwingConstants.CENTER), BorderLayout.NORTH);
		p.add(new JLabel(String.format("newest : %-6s", update.getRemoteVersion()), SwingConstants.CENTER), BorderLayout.SOUTH);
		
		panel.add(p, BorderLayout.NORTH);
		// center
		panel.add(new DetailPanel(update.getDescription(), DetailPanel.TextType.HTML).init(), BorderLayout.CENTER);
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