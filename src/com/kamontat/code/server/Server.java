package com.kamontat.code.server;

import com.kamontat.exception.UpdateException;

import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Wed 08/Mar/2017 - 10:20 PM
 */
public class Server {
	private static Server server;
	
	private URL url;
	
	public static Server connect(URL url) {
		if (server == null) server = new Server(url);
		return server;
	}
	
	public static Server connect() throws UpdateException {
		if (server == null) throw new UpdateException("No", "Can't initial server");
		return server;
	}
	
	private Server(URL url) {
		this.url = url;
	}
}
