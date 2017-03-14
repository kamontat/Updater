package main.java.com.kamontat.example;

import com.utilities.URLUtil;
import main.java.com.kamontat.code.server.Owner;
import main.java.com.kamontat.code.config.Configuration;
import main.java.com.kamontat.code.popup.DescriptionPopup;
import main.java.com.kamontat.code.server.GitUpdater;
import main.java.com.kamontat.code.server.Updater;
import main.java.com.kamontat.code.server.github.Assets;
import main.java.com.kamontat.code.utilities.MessageUtil;

import javax.swing.*;
import java.io.IOException;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 09/Mar/2017 - 12:03 AM
 */
public class Main {
	private static Owner owner = new Owner("kamontat", "CheckIDNumber");
	private static String currentVersion = "v3.0";
	
	private static Updater getUpdater() {
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
		// create popup
		DescriptionPopup popup = new DescriptionPopup(update) {
			@Override
			protected void downloadAction() {
				waiting();
				String path = update.download();
				done();
				Configuration.ALERT_COMPLETE_MESSAGE = String.format("The path is %s, \nDo you want to open it?", path);
				super.downloadAction();
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
