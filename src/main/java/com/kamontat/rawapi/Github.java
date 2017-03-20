package com.kamontat.rawapi;

import com.kamontat.code.model.github.Release;
import com.kamontat.exception.UpdateException;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:56 PM
 */
public interface Github {
	String GITHUB_API = "https://api.github.com/";
	String REPOS_API = "repos/";
	
	String MARKDOWN_CONVERTER_API = "markdown";
	String RELEASE_API = "releases";
	String LATEST_RELEASE_API = "releases/latest";
	
	// header of remaining rate-limit
	String RATE_REMAINING_HEADER = "X-RateLimit-Remaining";
	
	Github updateRemain() throws UpdateException;
	
	int getRemaining();
	
	Github updateRelease() throws UpdateException;
	
	Release getRelease();
	
	default boolean isOutOfRate() {
		return getRemaining() <= 0;
	}
}
