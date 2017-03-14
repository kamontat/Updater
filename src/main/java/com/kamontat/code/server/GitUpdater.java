package main.java.com.kamontat.code.server;

import main.java.com.kamontat.code.Owner;
import main.java.com.kamontat.code.server.github.GithubManagement;
import main.java.com.kamontat.code.server.github.Release;
import main.java.com.kamontat.exception.UpdateException;

/**
 * Precondition: <br>
 * <a href="https://github.com/FasterXML/jackson">jackson library</a>
 * <ul>
 * <li><a href="http://wiki.fasterxml.com/JacksonHome">WIKI</a></li>
 * <li><a href="https://github.com/FasterXML/jackson-docs">DOC</a></li>
 * </ul>
 * getting latest release from the github with anonymous auth (rate-limit: 60 times)
 *
 * @author kamontat
 * @version 1.0
 * @since 2/16/2017 AD - 11:25 PM
 */
public abstract class GitUpdater extends Updater {
	protected Release release;
	
	public GitUpdater(Owner owner) {
		super(owner);
		try {
			GithubManagement management = new GithubManagement(owner);
			release = management.getRelease();
			
			setVersion(release.type(Release.ReleaseTitle.TAG_NAME, String.class));
			setDownloadLink();
		} catch (UpdateException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getTitle() {
		if (release != null) return release.type(Release.ReleaseTitle.NAME, String.class);
		return "";
	}
	
	@Override
	public String getDescription() {
		if (release != null) return release.type(Release.ReleaseTitle.BODY, String.class);
		return "";
	}
}