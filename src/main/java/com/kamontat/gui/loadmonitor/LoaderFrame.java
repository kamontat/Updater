package com.kamontat.gui.loadmonitor;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.*;

public class LoaderFrame implements Runnable {
	private static JDialog dialog;
	
	private String title;
	private String note;
	private JProgressBar bar;
	
	public LoaderFrame(Component parent, String pageName, ProgressBarUI ui, int min, int max) {
		if (parent != null) {
			Window window = SwingUtilities.getWindowAncestor(parent);
			if (window instanceof Frame) dialog = new JDialog((Frame) window, pageName, true);
			else if (window instanceof Dialog) dialog = new JDialog((Dialog) window, pageName, true);
		}
		if (dialog == null) dialog = new JDialog((Frame) null, pageName, true);
		
		bar = new JProgressBar(min, max);
		if (ui != null) bar.setUI(ui);
	}
	
	public void setUI() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel("something"));
		p.add(bar, BorderLayout.CENTER);
		dialog.setContentPane(p);
	}
	
	
	public void setProgress(int n) {
		bar.setValue(n);
	}
	
	public int getProgress() {
		return bar.getValue();
	}
	
	@Override
	public void run() {
		
	}
}