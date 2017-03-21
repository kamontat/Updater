package com.kamontat.implementation;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.VersionFactory;
import com.kamontat.object.GithubManagement;
import com.kamontat.object.Owner;
import com.kamontat.object.Release;
import com.kamontat.rawapi.Github;
import com.kamontat.rawapi.Updatable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:20 AM
 */
public class GUpdater extends Updater {
	private static VersionFactory factory = VersionFactory.getInstance();
	private Github manager;
	
	public GUpdater(Owner owner, String currentVersion) throws UpdateException {
		super(owner);
		factory.setCurrentVersion(currentVersion);
		manager = new GithubManagement(super.owner);
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
		
		return this;
	}
}
