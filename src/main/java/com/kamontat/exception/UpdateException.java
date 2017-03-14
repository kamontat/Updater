package main.java.com.kamontat.exception;

import java.io.IOException;
import java.net.URL;

/**
 * updating error with occur by this exception
 *
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:22 PM
 */
public class UpdateException extends IOException {
	public static final String NO_LINK = "Don't have any link setting yet.";
	
	public UpdateException(URL url, String message) {
		this(url == null ? NO_LINK: url.toString(), message, null);
	}
	
	public UpdateException(String link, String message) {
		this(link, message, null);
	}
	
	public UpdateException(URL url, Exception e) {
		this(url == null ? NO_LINK: url.toString(), e.getMessage(), e.getCause());
	}
	
	public UpdateException(String link, Exception e) {
		this(link, e.getMessage(), e.getCause());
	}
	
	public UpdateException(String link, String message, Throwable t) {
		super(String.format("At: %s, Message: %s", link, message), t);
	}
}
