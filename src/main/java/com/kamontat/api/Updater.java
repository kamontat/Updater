package com.kamontat.api;

import com.kamontat.code.object.Owner;
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
	private Owner owner;
	private String name;
	private String description;
	private Downloadable download;
	
	@Override
	public void setVersion(Version version) {
		this.version = version;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
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
	public String getName() {
		return name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public Downloadable getDownload() {
		return download;
	}
}
