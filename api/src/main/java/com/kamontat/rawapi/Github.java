package com.kamontat.rawapi;

import com.kamontat.objects.Release;
import com.kamontat.exception.UpdateException;

/**
 * github interface and constant for request github-api <br>
 * All constants contains "/" in back of value
 *
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:56 PM
 */
public interface Github {
	/**
	 * github api link
	 */
	String GITHUB_API = "https://api.github.com/";
	/**
	 * repos link (can connect to {@link #GITHUB_API})
	 */
	String REPOS_API = "repos/";
	/**
	 * for get all release
	 */
	String RELEASE_API = "releases";
	/**
	 * for get only latest release
	 */
	String LATEST_RELEASE_API = "releases/latest";
	
	/**
	 * header of remaining rate-limit
	 */
	String RATE_REMAINING_HEADER = "X-RateLimit-Remaining";
	
	/**
	 * default of remaining for setting before update
	 */
	int DEFAULT_REMAINING = -99;
	
	/**
	 * update remain from server
	 *
	 * @return {@code this}
	 * @throws UpdateException
	 * 		updating error
	 */
	Github updateRemain() throws UpdateException;
	
	/**
	 * get current remaining
	 *
	 * @return remain number that can request
	 */
	int getRemaining();
	
	/**
	 * update latest release from server <br>
	 * If not release yet, it's will set as {@link Release#getEmptyRelease()}
	 *
	 * @return {@code this}
	 * @throws UpdateException
	 * 		updating error
	 */
	Github updateRelease() throws UpdateException;
	
	/**
	 * getting newest/latest release (might be {@link Release#getEmptyRelease()})
	 *
	 * @return newest/latest release
	 * @throws UpdateException
	 * 		updating error or not update yet
	 */
	Release getRelease() throws UpdateException;
	
	/**
	 * check is out of limit yet?
	 *
	 * @return true if out (meaning cannot check anymore)
	 */
	default boolean isOutOfLimit() {
		return getRemaining() <= 0;
	}
	
	/**
	 * check is already updated
	 *
	 * @return {@code true} if updated; otherwise, return {@code false}
	 */
	default boolean isUpdated() {
		try {
			return getRemaining() != DEFAULT_REMAINING && getRelease() != null;
		} catch (UpdateException e) {
			return false;
		}
	}
}
