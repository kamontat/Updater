package com.kamontat.factory;

import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Updatable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:21 AM
 */
public class UpdaterFactory {
	private static Updatable ourInstance;
	
	public static UpdaterFactory setUpdater(Updatable update) {
		if (ourInstance == null) {
			if (update == null) {
				throw new NullPointerException("update can't be null");
			}
			UpdaterFactory.ourInstance = update;
		}
		return new UpdaterFactory();
	}
	
	private UpdaterFactory() {
	}
	
	public void checkRelease() throws UpdateException {
		ourInstance.call();
	}
	
	public String download() throws UpdateException {
		return ourInstance.getDownload().download();
	}
	
	public boolean isLatest() {
		return ourInstance.isLatest();
	}
	
	public String getCurrentVersion() {
		return ourInstance.getVersion().getCurrentVersion();
	}
	
	public String getRemoteVersion() {
		return ourInstance.getVersion().getRemoteVersion();
	}
	
	public String getTitle() {
		return ourInstance.getTitle();
	}
	
	public String getDescription() {
		return ourInstance.getDescription();
	}
	
	public String getDownloadFileName() {
		return ourInstance.getDownload().getName();
	}
	
	public long getDownloadSize() {
		return ourInstance.getDownload().getSize();
	}
	
	public String getDownloadType() {
		return ourInstance.getDownload().getContentType();
	}
	
	public void delete() {
		ourInstance.delete();
	}
}
