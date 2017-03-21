package com.kamontat.api;

import com.kamontat.object.Owner;
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
	protected Version version;
	// project owner
	protected Owner owner;
	protected String title;
	protected String description;
	protected Downloadable download;
	
	public Updater(Owner owner) {
		this.owner = owner;
	}
	
	@Override
	public void setVersion(Version version) {
		this.version = version;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public void setDescription(String htmlDescription) {
		this.description = htmlDescription;
	}
	
	@Override
	public void setDownload(Downloadable link) {
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
