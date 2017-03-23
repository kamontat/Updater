package com.kamontat.factory;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Downloadable;
import com.kamontat.rawapi.Version;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:21 AM
 */
public class UpdaterFactory {
	private static Updater ourInstance;
	
	public static Updater getInstance() {
		if (ourInstance != null) return ourInstance;
		throw new NullPointerException("instance cannot be null");
	}
	
	public static void setUpdater(Updater update) {
		if (ourInstance == null) {
			UpdaterFactory.ourInstance = update;
		}
	}
	
	private UpdaterFactory() {
	}
	
	public void checkUpdate() throws UpdateException {
		ourInstance.call();
	}
	
	public Version getVersion() {
		return ourInstance.getVersion();
	}
	
	public String getTitle() {
		return ourInstance.getTitle();
	}
	
	public String getDescription() {
		return ourInstance.getDescription();
	}
	
	public Downloadable getDownload() {
		return ourInstance.getDownload();
	}
}
