package main.java.com.kamontat.code.server;

import com.utilities.FilesUtil;
import main.java.com.kamontat.code.Owner;

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
	public static String currentVersion = "v0.0.0";
	public static String remoteVersion;
	
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
	
	public String getCurrentVersion() {
		return currentVersion;
	}
	
	public String getVersion() throws IOException {
		if (remoteVersion == null) throw new IOException("remoteVersion unset.");
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
	 * if you use {@link main.java.com.kamontat.code.popup.DescriptionPopup} class, you no need to implement this method
	 */
	public void alert() {
		
	}
}
