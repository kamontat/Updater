package com.kamontat.implementation;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.DownloadFactory;
import com.kamontat.factory.VersionFactory;
import com.kamontat.objects.Assets;
import com.kamontat.objects.GithubManagement;
import com.kamontat.objects.Owner;
import com.kamontat.objects.Release;
import com.kamontat.rawapi.Github;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.FilesUtil;
import com.kamontat.utilities.URLManager;

import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:20 AM
 */
public class GUpdater extends Updater {
	private static VersionFactory factory = VersionFactory.getInstance();
	private Github manager;
	
	private int pos = -1;
	private URL url;
	
	public GUpdater(Owner owner, String currentVersion, int assetPosition) throws UpdateException {
		this(owner, currentVersion, null, assetPosition);
	}
	
	public GUpdater(Owner owner, String currentVersion, URL link) throws UpdateException {
		this(owner, currentVersion, link, -1);
	}
	
	private GUpdater(Owner o, String current, URL url, int asset) throws UpdateException {
		super(o);
		factory.setCurrentVersion(current);
		manager = new GithubManagement(super.owner);
		if (url == null) pos = asset;
		else this.url = url;
	}
	
	public Github getGithub() {
		return manager;
	}
	
	@Override
	public Updatable call() throws UpdateException {
		// updating
		Release release = manager.updateRemain().updateRelease().getRelease();
		// set remote version
		factory.setRemoteVersion(release.type(Release.ReleaseTitle.TAG_NAME, String.class));
		// set all information in updater
		setTitle(release.type(Release.ReleaseTitle.NAME, String.class));
		setDescription(release.type(Release.ReleaseTitle.BODY, String.class));
		setVersion(factory);
		// link setup
		if (pos != -1)
			url = URLManager.getUrl(release.getAsset(pos).get(Assets.AssetTitle.BROWSER_DOWNLOAD_URL)).getUrl();
		// download setup
		setDownload(DownloadFactory.setLocation(url, FilesUtil.getFileFromRoot().getAbsolutePath()));
		return this;
	}
}
