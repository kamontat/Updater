package com.kamontat.config;

import com.kamontat.objects.Release;

import java.io.Serializable;

/**
 * Before you use anything in this library, You might need to config some variable inside this class first.
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 8:03 PM
 */
public class Configuration implements Serializable {
	/**
	 * serial number
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * use to sent the message back when latest release not found
	 *
	 * @see Release#type(Release.ReleaseTitle, Class)
	 */
	public static String NOT_FOUND_RELEASE_MESSAGE = "Latest Release: not found";
	/**
	 * auto checking update program when open the program
	 */
	public static boolean isAutoUpdate = false;
}