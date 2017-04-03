package com.kamontat.exception;

import com.kamontat.annotation.NotNull;
import com.kamontat.annotation.Nullable;

import java.io.IOException;
import java.net.URL;

/**
 * updating error with occur by this exception <br>
 * If parameter that have {@code null} passing, it's will should DEFAULT message instead <br>
 * <ul>
 * <li>if {@code null} in link - {@link #DEFAULT_LINK} will use</li>
 * <li>if {@code null} in message - {@link #DEFAULT_MESSAGE} will use</li>
 * </ul>
 *
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:22 PM
 */
public class UpdateException extends IOException {
	private static final String DEFAULT_LINK = "Don't have any link setting yet.";
	private static final String DEFAULT_MESSAGE = "Some error occurred.";
	
	public UpdateException(@Nullable URL url, @Nullable String message) {
		this(url == null ? DEFAULT_LINK: url.toString(), message, null);
	}
	
	public UpdateException(@Nullable String link, @Nullable String message) {
		this(link, message, null);
	}
	
	public UpdateException(@Nullable URL url, @NotNull Exception e) {
		this(url == null ? DEFAULT_LINK: url.toString(), e.getMessage(), e.getCause());
	}
	
	public UpdateException(@Nullable String link, @NotNull Exception e) {
		this(link, e.getMessage(), e.getCause());
	}
	
	public UpdateException(String link, String message, @Nullable Throwable t) {
		super(String.format("At: %s, Message: %s", link == null ? DEFAULT_LINK: link, message == null ? DEFAULT_MESSAGE: message), t);
	}
}
