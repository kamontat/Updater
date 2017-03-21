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
	
	void setVersion(Version version);
	
	Version getVersion();
	
	void setTitle(String title);
	
	String getTitle();
	
	void setDescription(String htmlDescription);
	
	String getDescription();
	
	void setDownload(Downloadable link);
	
	Downloadable getDownload();
	
	default boolean isLatest() {
		Version v = getVersion();
		return v.getCurrentVersion().equals(v.getRemoteVersion());
	}
	
	/**
	 * For update information from server
	 *
	 * @return {@link Updatable}
	 * @throws UpdateException
	 * 		if error occurred while updating
	 */
	Updatable call() throws UpdateException;
}
