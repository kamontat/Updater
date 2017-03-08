package com.kamontat.exception;

import java.net.URL;

/**
 * updating error with occur by this exception
 *
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:22 PM
 */
public class UpdateException extends Exception {
	public UpdateException(URL url, String message) {
		super(String.format("error_url: %s, cause: %s", url == null ? "null": url.toString(), message));
	}
	
	public UpdateException(String link, String message) {
		super(String.format("error_url: %s, cause: %s", link, message));
	}
}
