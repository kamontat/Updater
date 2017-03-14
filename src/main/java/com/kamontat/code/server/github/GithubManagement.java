package main.java.com.kamontat.code.server.github;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.kamontat.code.server.Owner;
import main.java.com.kamontat.exception.UpdateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 13/Mar/2017 - 9:11 PM
 */
public class GithubManagement {
	private static final String GITHUB_API = "https://api.github.com/";
	private static final String REPOS_API = "repos/";
	
	private static final String MARKDOWN_CONVERTER_API = "markdown";
	private static final String RELEASE_API = "releases";
	private static final String LATEST_RELEASE_API = "releases/latest";
	
	// header of remaining rate-limit
	private static final String RATE_REMAINING_HEADER = "X-RateLimit-Remaining";
	// json parse using jackson api
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private URL url;
	public int remaining;
	private Release release;
	
	/**
	 * constructor of update, (might be slow)<br>
	 * <ol>
	 * <li>check remaining limit</li>
	 * <li>convert url respond body to json and add to release</li>
	 * <li>return the latest release</li>
	 * </ol>
	 *
	 * @throws UpdateException
	 * 		<ul>
	 * 		<li>json parsing error => can't parse respond body to json</li>
	 * 		<li>{@link #getUrl(String)}</li>
	 * 		<li>{@link #getConnection(URL)} </li>
	 * 		</ul>
	 */
	public GithubManagement(Owner o) throws UpdateException {
		String link = GITHUB_API + REPOS_API + o.getName() + "/" + o.getProjectName() + "/" + LATEST_RELEASE_API;
		url = getUrl(link);
		// get rate-limit
		remaining = Integer.parseInt(getConnection(url).getHeaderField(RATE_REMAINING_HEADER));
		
		// converting json to object
		try {
			JsonNode node = mapper.readTree(url);
			release = new Release(node);
		} catch (FileNotFoundException e) {
			release = Release.getEmptyRelease();
		} catch (IOException e) {
			throw new UpdateException(url, e.getMessage());
		}
	}
	
	public Release getRelease() {
		return release;
	}
	
	/**
	 * get url
	 *
	 * @param link
	 * 		link must content http:// or https://
	 * @return {@link URL} of the link
	 * @throws UpdateException
	 * 		url error => no protocol, string can't parse
	 */
	private static URL getUrl(String link) throws UpdateException {
		try {
			return new URL(link.toLowerCase(Locale.ENGLISH));
		} catch (MalformedURLException e) {
			throw new UpdateException(link, e);
		}
	}
	
	/**
	 * get connection of url
	 *
	 * @param url
	 * 		the url to connect
	 * @return https url connection
	 * @throws UpdateException
	 * 		if I/O occurred
	 */
	private static HttpURLConnection getConnection(URL url) throws UpdateException {
		try {
			return (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new UpdateException(url, e);
		}
	}
}
