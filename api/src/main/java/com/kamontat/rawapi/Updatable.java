package com.kamontat.rawapi;

import com.kamontat.api.FileManagement;
import com.kamontat.exception.UpdateException;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * This update able <br>
 * If you want to implement you might use {@link com.kamontat.api.Updater} instead of this
 *
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
		return v != null && v.getCurrentVersion().equals(v.getRemoteVersion());
	}
	
	/**
	 * <b>beware</b>: this method will delete all file in current folder/project <br>
	 * Using from delete old version is enough
	 */
	default void delete() {
		FileManagement.removeThis();
	}
	
	/**
	 * For update information from server, <b>NOT</b> for download file. <br>
	 * The download file should stay at {@link Downloadable#download(Runnable)} method when runnable is <b>optional</b> code that will run while loading progress
	 *
	 * @return {@link Updatable}
	 * @throws UpdateException
	 * 		if error occurred while updating
	 */
	Updatable call() throws UpdateException;
}
