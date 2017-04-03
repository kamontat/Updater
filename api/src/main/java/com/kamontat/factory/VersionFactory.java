package com.kamontat.factory;

import com.kamontat.rawapi.Version;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:41 AM
 */
public class VersionFactory implements Version {
	private static VersionFactory instance;
	
	private String current;
	private String remote;
	
	public static VersionFactory getInstance() {
		if (instance == null) instance = new VersionFactory();
		return instance;
	}
	
	private VersionFactory() {
	}
	
	public void setCurrentVersion(String currentVersion) {
		current = currentVersion;
	}
	
	public void setRemoteVersion(String remoteVersion) {
		remote = remoteVersion;
	}
	
	public boolean hasVersion() {
		return current != null && remote != null;
	}
	
	@Override
	public String getCurrentVersion() {
		return current == null ? DEFAULT_VERSION: current;
	}
	
	@Override
	public String getRemoteVersion() {
		return remote == null ? DEFAULT_VERSION: remote;
	}
	
	@Override
	public String toString() {
		return "VersionFactory{" + "current='" + current + '\'' + ", remote='" + remote + '\'' + '}';
	}
}
