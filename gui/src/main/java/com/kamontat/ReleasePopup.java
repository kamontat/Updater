package com.kamontat;

import com.kamontat.component.DetailPanel;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.rawapi.Updatable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * The popup that contains update description and downloader link.
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 2:27 PM
 */
public class ReleasePopup extends AbstractAction implements Runnable {
	private static JDialog dialog;
	private Point point;
	private UpdaterFactory update;
	
	/**
	 * Constructor to create popup <br>
	 *
	 * @param update
	 * 		The updater
	 */
	public ReleasePopup(Updatable update) throws UpdateException {
		this(update, null);
	}
	
	public ReleasePopup(UpdaterFactory update) throws UpdateException {
		this(update, null);
	}
	
	public ReleasePopup(Updatable update, Point point) throws UpdateException {
		this(UpdaterFactory.setUpdater(update), point);
	}
	
	public ReleasePopup(UpdaterFactory update, Point point) throws UpdateException {
		this.point = point;
		this.update = update;
		update.checkRelease();
	}
	
	private JPanel setPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		// north
		JPanel p = new JPanel(new BorderLayout());
		if (!update.isLatest()) {
			p.add(new JLabel(String.format("current: %-6s", update.getCurrentVersion()), SwingConstants.CENTER), BorderLayout.NORTH);
			p.add(new JLabel(String.format("newest : %-6s", update.getRemoteVersion()), SwingConstants.CENTER), BorderLayout.SOUTH);
		} else {
			p.add(new JLabel("Up to date (" + update.getCurrentVersion() + ")", SwingConstants.CENTER), BorderLayout.CENTER);
		}
		
		panel.add(p, BorderLayout.NORTH);
		// center
		panel.add(new DetailPanel(update.getDescription(), DetailPanel.TextType.HTML).init(), BorderLayout.CENTER);
		// south
		if (!update.isLatest()) {
			JButton button = new JButton("update!");
			button.addActionListener(this);
			panel.add(button, BorderLayout.SOUTH);
		}
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
		if (Objects.nonNull(point)) dialog.setLocation(point);
		dialog.pack();
		dialog.setVisible(true);
	}
}