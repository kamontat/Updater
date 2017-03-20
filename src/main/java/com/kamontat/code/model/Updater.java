package com.kamontat.code.model;

import com.utilities.FilesUtil;

import com.kamontat.code.gui.ReleasePopup;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:27 PM
 */
public abstract class Updater {
	/**
	 * must set this first
	 */
	private static String currentVersion = "v0.0.0";
	private static String remoteVersion = "v0.0.0";
	
	private URL downloadLink;
	private String fileName;
	private Owner owner;
	
	
	public Updater(Owner owner) {
		this.owner = owner;
	}
	
	/**
	 * To implement this class you need to call this method in constructor <br>
	 * must set this method before do another thing
	 *
	 * @param downloadLink
	 * 		download link
	 */
	protected void setDownloadLink(URL downloadLink) {
		this.downloadLink = downloadLink;
		String link = downloadLink.toString();
		fileName = link.substring(link.lastIndexOf('/') + 1, link.length());
	}
	
	/**
	 * To implement this class you need to call this method in constructor <br>
	 * must set this method before do another thing
	 *
	 * @param version
	 * 		the remote remoteVersion
	 */
	protected void setVersion(String version) {
		remoteVersion = version;
	}
	
	public Owner getOwner() {
		return owner;
	}
	
	public URL getDownload() {
		return downloadLink;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public static void setCurrentVersion(String currentVersion) {
		Updater.currentVersion = currentVersion;
	}
	
	public static String getCurrentVersion() {
		return currentVersion;
	}
	
	public static String getRemoteVersion() {
		return remoteVersion;
	}
	
	public String download() {
		String fileName = getFileName();
		File project = FilesUtil.getFile();
		while (!project.isDirectory()) project = project.getParentFile();
		Path target = project.toPath().resolve(fileName);
		try {
			Files.copy(getDownload().openStream(), target, StandardCopyOption.REPLACE_EXISTING);
			return target.toString();
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean isLatest() {
		return currentVersion.equals(remoteVersion);
	}
	
	public abstract String getTitle();
	
	public abstract String getDescription();
	
	public abstract void setDownloadLink();
	
	/**
	 * if you use {@link ReleasePopup} class, you no need to implement this method
	 */
	public void alert() {
		
	}
}