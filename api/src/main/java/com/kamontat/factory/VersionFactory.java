package com.kamontat.factory;

import com.kamontat.rawapi.Version;

/**
 * since {@link Version} is needed to know both current and remote version, so this class implemented for solve that problem, so the methd that you should use is get and set method ({@link #setCurrentVersion(String)}, {@link #setRemoteVersion(String)}, {@link #getCurrentVersion()}, {@link #getRemoteVersion()}) <br>
 * Important: This class is <b>singleton</b>
 *
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
	
	/**
	 * if there no setting current version, the default value will be used ({@value DEFAULT_VERSION})
	 *
	 * @return current version
	 */
	@Override
	public String getCurrentVersion() {
		return current == null ? DEFAULT_VERSION: current;
	}
	
	/**
	 * if there no setting remote version, the default value will be used ({@value DEFAULT_VERSION})
	 *
	 * @return remote version
	 */
	@Override
	public String getRemoteVersion() {
		return remote == null ? DEFAULT_VERSION: remote;
	}
	
	@Override
	public String toString() {
		return "VersionFactory{" + "current='" + current + '\'' + ", remote='" + remote + '\'' + '}';
	}
}
