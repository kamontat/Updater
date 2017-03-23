package com.kamontat.objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Github;
import com.kamontat.utilities.URLsUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 13/Mar/2017 - 9:11 PM
 */
public class GithubManagement implements Github {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private URL url;
	private int remaining = -99;
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
		url = URLsUtil.getUrl(link).getUrl();
		if (url == null) throw new UpdateException(link, "error occurred");
		updateRemain();
	}
	
	@Override
	public boolean isUpdated() {
		return remaining != -99 && url != null && release != null;
	}
	
	@Override
	public synchronized Github updateRemain() throws UpdateException {
		try {
			remaining = Integer.parseInt(URLsUtil.getUrl(url).getHttpConnection().getHeaderField(RATE_REMAINING_HEADER));
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
