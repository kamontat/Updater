package com.kamontat.factory;

import com.kamontat.annotation.LongMethod;
import com.kamontat.annotation.Nullable;
import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.URLReader;

import java.net.URL;

/**
 * Factory for {@link Updatable}, easy and to use but hard to manage <br>
 * This is <b>optional</b>, you might use directly
 *
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
	
	/**
	 * Just update information of latest release
	 *
	 * @throws UpdateException
	 * 		Error occurred when checking
	 */
	@LongMethod
	public void checkRelease() throws UpdateException {
		ourInstance.call();
	}
	
	/**
	 * download file to setting location {@link DownloadFactory#createDownloadable(URL, String)}
	 *
	 * @return location/path that actually place
	 * @throws UpdateException
	 * 		Error occurred when checking
	 */
	@LongMethod
	public String download(@Nullable Runnable action) throws UpdateException {
		return ourInstance.getDownload().download(action);
	}
	
	/**
	 * <b>Precondition</b>: should call {@link #checkRelease()} first <br>
	 * check is latest version
	 *
	 * @return true if latest; otherwise return false
	 */
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
	
	/**
	 * in case you need to implement loading action by yourself <br>
	 * <b>Allow method</b>:
	 * <ol>
	 * <li>{@link URLReader#getTotalByte()} - For getting total byte to loaded</li>
	 * <li>{@link URLReader#getBytesRead()}  - For getting current byte are already loaded</li>
	 * <li>{@link URLReader#getOutputFile()} - For getting location that file will placed</li>
	 * </ol>
	 * <b>if use other method may cause unexpected result.</b>
	 *
	 * @return reader
	 */
	public URLReader getURLReader() {
		return ourInstance.getDownload().getReader();
	}
	
	public void delete() {
		ourInstance.delete();
	}
}
