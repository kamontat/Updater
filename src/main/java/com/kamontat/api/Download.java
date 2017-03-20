package com.kamontat.api;

import com.kamontat.rawapi.Downloadable;

import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:48 PM
 */
public class Download implements Downloadable {
	
	@Override
	public URL getDownloadLink() {
		return null;
	}
	
	@Override
	public void download() {
	}
	
	@Override
	public void alert() {
		
	}
}
