package com.kamontat.rawapi;

import java.io.Serializable;

/**
 * Version management, implemented at {@link com.kamontat.factory.VersionFactory}
 *
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:41 PM
 */
public interface Version extends Serializable {
	long serialVersionUID = 1L;
	
	/**
	 * default version will return if no version setting
	 */
	String DEFAULT_VERSION = "v0.0.0";
	
	/**
	 * get current program or local program version
	 *
	 * @return string version
	 */
	String getCurrentVersion();
	
	/**
	 * get latest program version from server
	 *
	 * @return string version
	 */
	String getRemoteVersion();
}
