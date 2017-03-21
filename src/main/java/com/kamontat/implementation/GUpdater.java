package com.kamontat.implementation;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.DownloadFactory;
import com.kamontat.factory.VersionFactory;
import com.kamontat.object.Assets;
import com.kamontat.object.GithubManagement;
import com.kamontat.object.Owner;
import com.kamontat.object.Release;
import com.kamontat.rawapi.Github;
import com.kamontat.rawapi.Updatable;
import com.kamontat.utilities.FilesUtil;
import com.kamontat.utilities.URLsUtil;

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
	
	@Override
	public Updatable call() throws UpdateException {
		// updating
		manager.updateRemain();
		manager.updateRelease();
		// set remote version
		factory.setRemoteVersion(manager.getRelease().type(Release.ReleaseTitle.TAG_NAME, String.class));
		// set all information in updater
		super.title = manager.getRelease().type(Release.ReleaseTitle.NAME, String.class);
		super.description = manager.getRelease().type(Release.ReleaseTitle.BODY, String.class);
		super.version = factory;
		// link setup
		if (pos != -1)
			url = URLsUtil.getUrl(manager.getRelease().getAsset(pos).get(Assets.AssetTitle.DOWNLOAD_URL)).getUrl();
		// download setup
		DownloadFactory.setLocation(url, FilesUtil.getFileFromRoot().getAbsolutePath());
		super.download = DownloadFactory.getInstance().getDownload();
		return this;
	}
}
