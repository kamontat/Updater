package com.kamontat.rawapi;

import com.kamontat.exception.UpdateException;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:37 PM
 */
public interface Updatable extends Callable<Updatable>, Serializable {
	long serialVersionUID = 1L;
	
	Version getVersion();
	
	String getTitle();
	
	String getDescription();
	
	Downloadable getDownload();
	
	default boolean isLatest() {
		Version v = getVersion();
		return v.getCurrentVersion().equals(v.getRemoteVersion());
	}
	
	/**
	 * For update information from server <br>
	 * <b>NOT</b> for download file, The download file should stay at {@link Downloadable#download()} method
	 *
	 * @return {@link Updatable}
	 * @throws UpdateException
	 * 		if error occurred while updating
	 */
	Updatable call() throws UpdateException;
}
