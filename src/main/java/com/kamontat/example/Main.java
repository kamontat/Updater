package main.java.com.kamontat.example;

import com.utilities.URLUtil;
import main.java.com.kamontat.code.Owner;
import main.java.com.kamontat.code.popup.DescriptionPopup;
import main.java.com.kamontat.code.server.GitUpdater;
import main.java.com.kamontat.code.server.Updater;
import main.java.com.kamontat.code.server.github.Assets;
import main.java.com.kamontat.code.utilities.DesktopAction;
import main.java.com.kamontat.code.utilities.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 09/Mar/2017 - 12:03 AM
 */
public class Main {
	private static Owner owner = new Owner("kamontat", "CheckIDNumber");
	public static String currentVersion = "v3.0";
	
	public static Updater getUpdater() {
		// set current remoteVersion
		Updater.currentVersion = currentVersion;
		// config class behavior
		return new GitUpdater(owner) {
			@Override
			public void setDownloadLink() {
				String link = release.getAsset(0).get(Assets.AssetTitle.BROWSER_DOWNLOAD_URL);
				try {
					setDownloadLink(URLUtil.getUrl(link).getUrl());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static void main(String[] args) throws IOException {
		Updater update = getUpdater();
		DescriptionPopup popup = new DescriptionPopup(update) {
			@Override
			protected void downloadAction() {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				String path = update.download();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				String message = String.format("The path is %s, \nDo you want to open it?", path);
				int result = JOptionPane.showConfirmDialog(dialog, message, "Download complete!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (result == JOptionPane.YES_OPTION) {
					DesktopAction.get().open(new File(path));
				}
			}
		};
		
		// if not latest
		if (!update.isLatest()) {
			SwingUtilities.invokeLater(popup);
		} else {
			MessageUtil.UP_TO_DATE();
		}
	}
}
