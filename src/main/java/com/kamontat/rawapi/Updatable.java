package com.kamontat.rawapi;

import java.io.Serializable;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:37 PM
 */
public interface Updatable extends Serializable {
	long serialVersionUID = 1L;
	
	void setVersion(Version version);
	
	Version getVersion();
	
	void setName(String name);
	
	String getName();
	
	void setDescription(String description);
	
	String getDescription();
	
	void setDownload(Downloadable link);
	
	Downloadable getDownload();
	
	void check();
	
	default boolean isLatest() {
		Version v = getVersion();
		return v.getCurrentVersion().equals(v.getRemoteVersion());
	}
}
