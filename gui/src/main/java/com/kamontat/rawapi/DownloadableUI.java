package com.kamontat.rawapi;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 30/Apr/2017 - 4:59 PM
 */
public interface DownloadableUI<T, R> {
	void preDownload();
	
	void postDownload(R result);
	
	R download(T... input);
	
	default void execute(T... input) {
		preDownload();
		R r = download(input);
		postDownload(r);
	}
}
