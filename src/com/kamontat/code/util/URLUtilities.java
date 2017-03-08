package com.kamontat.code.util;

import com.kamontat.exception.UpdateException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:30 PM
 */
public class URLUtilities {
	public enum Protocol {
		HTTP, HTTPS;
	}
	
	private Protocol protocol;
	private URL url;
	
	private URLUtilities(Protocol protocol, String link) throws UpdateException {
		this.protocol = protocol;
		// add protocol
		link = (protocol == Protocol.HTTP ? "http://": "https://") + link;
		
		try {
			url = new URL(link.toLowerCase(Locale.ENGLISH));
		} catch (MalformedURLException e) {
			throw new UpdateException(link, "URL ERROR: " + e.getMessage());
		}
	}
	
	public URL getUrl() {
		return url;
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
	public static URLUtilities getUrl(Protocol protocol, String link) throws UpdateException {
		return new URLUtilities(protocol, link.toLowerCase(Locale.ENGLISH));
	}
	
	/**
	 * get https connection of url (only for https protocol)
	 *
	 * @return https url connection
	 * @throws UpdateException
	 * 		if I/O occurred
	 */
	public HttpsURLConnection getHttpsConnection() throws UpdateException {
		try {
			return (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			throw new UpdateException(url, "IO ERROR OCCURRED");
		} catch (ClassCastException e) {
			throw new UpdateException(url, "Isn't HTTPS PROTOCOL");
		}
	}
	
	public <T extends URLConnection> T getConnection(Class<T> tClass) throws UpdateException {
		try {
			return tClass.cast(url.openConnection());
		} catch (IOException e) {
			throw new UpdateException(url, "IO ERROR OCCURRED");
		} catch (ClassCastException e) {
			throw new UpdateException(url, e.getMessage());
		}
	}
	
	public URLConnection getConnection() throws UpdateException {
		return getConnection(URLConnection.class);
	}
	
	public String getContent() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line).append("\n");
		}
		return builder.toString();
	}
	
	/**
	 * Get the size in bytes of the resource at the specified url.
	 */
	public int getURLSize() {
		if (url == null) return 0;
		try {
			return getConnection().getContentLength();
		} catch (UpdateException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * Get filename portion of URL.  Result may be an empty string.
	 */
	public String getURLFilename() {
		String filename = url.getPath();
		int k = filename.lastIndexOf("/");
		if (k == filename.length() - 1) return "";
		if (k >= 0) filename = filename.substring(k + 1);
		return filename;
	}
	
	/**
	 * A debugging tools
	 */
	public void printHeader() {
		try {
			Map header = getConnection().getHeaderFields();
			for (Object key : header.keySet()) {
				System.out.printf("%s: %s\n", key, header.get(key));
			}
		} catch (UpdateException e) {
			e.printStackTrace();
		}
	}
}
