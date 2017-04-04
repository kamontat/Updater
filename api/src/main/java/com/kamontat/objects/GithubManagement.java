package com.kamontat.objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Github;
import com.kamontat.utilities.URLManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.kamontat.utilities.URLManager.HTTP_CONNECTION;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 13/Mar/2017 - 9:11 PM
 */
public class GithubManagement implements Github {
	/**
	 * For pass url {@code json} to {@link Map}
	 */
	private static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * latest release link
	 */
	private URL url;
	private int remaining = DEFAULT_REMAINING;
	private Release release;
	
	/**
	 * constructor of update, this just update rate limit - you can get by {@link #getRemaining()} <br>
	 * So you need to call {@link #updateRelease()} manually
	 *
	 * @param o
	 * 		The owner project
	 * @throws UpdateException
	 * 		some error occurred when try to create URL or open connection
	 */
	public GithubManagement(Owner o) throws UpdateException {
		String link = GITHUB_API + REPOS_API + o.getName() + "/" + o.getProjectName() + "/" + LATEST_RELEASE_API;
		url = URLManager.getUrl(link).getUrl();
		if (url == null) throw new UpdateException(link, "error occurred");
		updateRemain();
	}
	
	@Override
	public synchronized Github updateRemain() throws UpdateException {
		try {
			remaining = Integer.parseInt(URLManager.getUrl(url).getConnection(HTTP_CONNECTION).getHeaderField(RATE_REMAINING_HEADER));
		} catch (Exception e) {
			throw new UpdateException(url, e);
		}
		return this;
	}
	
	@Override
	public int getRemaining() {
		return remaining;
	}
	
	@Override
	public synchronized Github updateRelease() throws UpdateException {
		if (isOutOfLimit()) throw new UpdateException(url, "out of rate. please wait for a while");
		try {
			JsonNode node = mapper.readTree(url);
			release = new Release(node);
		} catch (FileNotFoundException e) {
			release = Release.getEmptyRelease();
		} catch (IOException e) {
			throw new UpdateException(url, e.getMessage());
		}
		return this;
	}
	
	@Override
	public Release getRelease() throws UpdateException {
		if (isUpdated()) return release;
		throw new UpdateException(url, "Not update yet");
	}
}
