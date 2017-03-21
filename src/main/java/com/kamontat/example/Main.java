package com.kamontat.example;

import com.kamontat.config.Configuration;
import com.kamontat.gui.ReleasePopup;
import com.kamontat.model.GitUpdater;
import com.kamontat.object.Owner;
import com.kamontat.model.Updater;
import com.kamontat.object.Assets;
import com.kamontat.utilities.MessageUtil;
import com.kamontat.utilities.URLsUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 09/Mar/2017 - 12:03 AM
 */
public class Main {
	private static Owner owner = new Owner("kamontat", "CheckIDNumber");
	private static String currentVersion = "v1.0";
	
	private static Updater getUpdater() {
		// set current remoteVersion
		// Updater.setCurrentVersion(currentVersion);
		// config class behavior
		return new GitUpdater(owner) {
			@Override
			public void setDownloadLink() {
				String link = release.getAsset(0).get(Assets.AssetTitle.BROWSER_DOWNLOAD_URL);
				setDownloadLink(URLsUtil.getUrl(link).getUrl());
			}
		};
	}
	
	public static void main(String[] args) throws IOException {
		Updater update = getUpdater();
		ReleasePopup popup = new ReleasePopup(update) {
			@Override
			public void actionPerformed(ActionEvent e) {
				waiting();
				String path = update.download();
				done();
				Configuration.ALERT_COMPLETE_MESSAGE = String.format("The path is %s, \nDo you want to open it?", path);
				super.actionPerformed(e);
			}
		};
		
		if (!update.isLatest()) {
			SwingUtilities.invokeLater(popup);
		} else {
			MessageUtil.UP_TO_DATE();
		}
	}
}
