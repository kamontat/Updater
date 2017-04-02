package com.kamontat.api;

import com.kamontat.objects.Owner;
import com.kamontat.rawapi.Downloadable;
import com.kamontat.rawapi.Updatable;
import com.kamontat.rawapi.Version;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:39 PM
 */
public abstract class Updater implements Updatable {
	// project version (include both current and remote)
	private Version version;
	// project owner
	protected Owner owner;
	private String title;
	private String description;
	private Downloadable download;
	
	public Updater(Owner owner) {
		this.owner = owner;
	}
	
	protected void setVersion(Version version) {
		this.version = version;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}
	
	protected void setDescription(String htmlDescription) {
		this.description = htmlDescription;
	}
	
	protected void setDownload(Downloadable link) {
		download = link;
	}
	
	@Override
	public Version getVersion() {
		return version;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public Downloadable getDownload() {
		return download;
	}
	
	@Override
	public String toString() {
		return "Updater{" + "version=" + version + ", owner=" + owner + ", title='" + title + '\'' + ", description='" + description + '\'' + ", download=" + download + '}';
	}
}
