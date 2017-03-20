package com.kamontat.rawapi;

import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:38 PM
 */
public interface Downloadable {
	URL getDownloadLink();
	
	void download();
	
	void alert();
}
