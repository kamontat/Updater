package com.kamontat.api;

import com.kamontat.annotation.NotNull;
import com.kamontat.annotation.Nullable;
import com.kamontat.config.Configuration;
import com.kamontat.exception.UpdateException;
import com.kamontat.objects.Owner;
import com.kamontat.rawapi.Downloadable;
import com.kamontat.rawapi.Updatable;
import com.kamontat.rawapi.Version;
import com.kamontat.utilities.HtmlUtil;

/**
 * Example implement is {@link com.kamontat.implementation.GUpdater} class, check it. <br>
 * This abstract class use for update information and also download the latest release <br>
 * Shouldn't <b>new</b> it, please implement it for using
 *
 * @author kamontat
 * @version 1.0
 * @see com.kamontat.config.Configuration
 * @since Mon 20/Mar/2017 - 10:39 PM
 */
public abstract class Updater implements Updatable {
	/**
	 * project version (include both current and remote), {@link Version}
	 */
	private Version version;
	/**
	 * project owner (important for github api; otherwise, up to you)
	 */
	protected Owner owner;
	/**
	 * title of release <br>
	 * Example: <i>Fixed important security bug</i>
	 */
	@Nullable
	private String title;
	/**
	 * description of release <br>
	 * should be <b>html</b> or <b>markdown</b>
	 */
	@Nullable
	private String description;
	/**
	 * download manager (include get, size, link and download action) {@link Downloadable}
	 */
	private Downloadable download;
	
	public Updater(Owner owner) {
		this.owner = owner;
	}
	
	/**
	 * For easy to use I provide factory already at {@link com.kamontat.factory.VersionFactory}
	 *
	 * @param version
	 * 		the {@link Version}
	 */
	protected void setVersion(Version version) {
		this.version = version;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}
	
	protected void setDescription(String htmlDescription) {
		this.description = htmlDescription;
	}
	
	/**
	 * For easy to use I provide factory already at {@link com.kamontat.factory.DownloadFactory}
	 *
	 * @param link
	 * 		{@link Downloadable} or {@link com.kamontat.factory.DownloadFactory#download}
	 */
	protected void setDownload(@NotNull Downloadable link) {
		download = link;
	}
	
	@Override
	public Version getVersion() {
		return version;
	}
	
	@Override
	public String getTitle() {
		if (title == null) title = Configuration.NOT_FOUND_RELEASE_MESSAGE;
		return title;
	}
	
	@Override
	public String getDescription() {
		if (description == null) return HtmlUtil.toHtmlByHtml("").toString();
		return description;
	}
	
	@Override
	public Downloadable getDownload() {
		if (download == null) throw new UpdateException(UpdateException.nullURL, "No download file");
		return download;
	}
	
	@Override
	public String toString() {
		return "Updater{" + "version=" + version + ", owner=" + owner + ", title='" + title + '\'' + ", description='" + description + '\'' + ", download=" + download + '}';
	}
}