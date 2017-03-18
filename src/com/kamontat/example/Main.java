package com.kamontat.example;

import com.kamontat.code.config.Configuration;
import com.kamontat.code.gui.ReleasePopup;
import com.kamontat.code.model.GitUpdater;
import com.kamontat.code.model.Owner;
import com.kamontat.code.model.Updater;
import com.kamontat.code.model.github.Assets;
import com.kamontat.code.utilities.MessageUtil;
import com.utilities.URLUtil;

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
		Updater.setCurrentVersion(currentVersion);
		
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
