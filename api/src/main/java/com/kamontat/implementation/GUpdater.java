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
 * Implementation of {@link Updater} for Github release
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:20 AM
 */
public class GUpdater extends Updater {
	private static VersionFactory factory = VersionFactory.getInstance();
	private Github manager;
	/**
	 * (optional) position of asset in github release (download file)
	 * <br>
	 * This start from <b>1...n</b>
	 */
	private int pos = -1;
	/**
	 * the link of download file
	 */
	private URL url;
	
	/**
	 * Create new Github updater
	 *
	 * @param owner
	 * 		project owner ({@link Owner})
	 * @param currentVersion
	 * 		current version (you can pass {@code null} and set later by {@link VersionFactory})
	 * @param assetPosition
	 * 		asset position of file (start from 1...n)
	 * @throws UpdateException
	 * 		error when creating URL or open stream
	 */
	public GUpdater(Owner owner, String currentVersion, int assetPosition) throws UpdateException {
		this(owner, currentVersion, null, assetPosition);
	}
	
	/**
	 * Create new Github updater
	 *
	 * @param owner
	 * 		project owner ({@link Owner})
	 * @param currentVersion
	 * 		current version (you can pass {@code null} and set later by {@link VersionFactory})
	 * @param link
	 * 		download link
	 * @throws UpdateException
	 * 		error when creating URL or open stream
	 */
	public GUpdater(Owner owner, String currentVersion, URL link) throws UpdateException {
		this(owner, currentVersion, link, -1);
	}
	
	/**
	 * {@code private} github updater (main constructor)
	 * either {@code url} or {@code asset} must not be {@code null}
	 *
	 * @param o
	 * 		owner
	 * @param current
	 * 		current version
	 * @param url
	 * 		(optional) download link
	 * @param asset
	 * 		(optional) position of asset
	 * @throws UpdateException
	 * 		error when creating URL or open stream
	 */
	private GUpdater(Owner o, String current, URL url, int asset) throws UpdateException {
		super(o);
		factory.setCurrentVersion(current);
		manager = new GithubManagement(super.owner);
		if (url == null) pos = asset;
		else this.url = url;
	}
	
	/**
	 * get github manager
	 *
	 * @return manager
	 */
	public Github getGithub() {
		return manager;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 * @throws UpdateException
	 * 		{@inheritDoc}
	 */
	@Override
	public Updatable call() throws UpdateException {
		// updating
		Release release = manager.updateRemain().updateRelease().getRelease();
		// set remote version
		factory.setRemoteVersion(release.get(Release.ReleaseTitle.TAG_NAME, String.class));
		// set all information in updater
		setTitle(release.get(Release.ReleaseTitle.NAME, String.class));
		setDescription(release.get(Release.ReleaseTitle.BODY, String.class));
		setVersion(factory);
		// link setup
		if (pos != -1)
			url = URLManager.getUrl(release.getAsset(pos).get(Assets.AssetTitle.BROWSER_DOWNLOAD_URL)).getUrl();
		// download setup
		setDownload(DownloadFactory.createDownloadable(url, FilesUtil.getFileFromRoot().getAbsolutePath()));
		return this;
	}
}
