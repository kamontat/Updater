package com.kamontat.rawapi;

import java.io.Serializable;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:41 PM
 */
public interface Version extends Serializable {
	long serialVersionUID = 1L;
	
	String DEFAULT_VERSION = "v0.0.0";
	
	String getCurrentVersion();
	
	String getRemoteVersion();
}
