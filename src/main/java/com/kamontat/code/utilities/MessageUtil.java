package main.java.com.kamontat.code.utilities;

import main.java.com.kamontat.code.server.Updater;

import javax.swing.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 3:39 PM
 */
public class MessageUtil {
	public static void UP_TO_DATE() {
		JOptionPane.showMessageDialog(null, "current version (" + Updater.currentVersion + ") is up to date. \nremote version(" + Updater.remoteVersion + ").", "Every thing up to date", JOptionPane.INFORMATION_MESSAGE);
	}
}
