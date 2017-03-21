package com.kamontat.factory;

import com.kamontat.api.FileManagement;
import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Downloadable;

import java.net.URL;
import java.util.concurrent.Callable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 10:16 AM
 */
public class DownloadFactory {
	private static DownloadFactory instance;
	
	private Downloadable download;
	private static Callable<String> call;
	
	public static DownloadFactory getInstance() {
		if (instance == null) instance = new DownloadFactory();
		return instance;
	}
	
	private DownloadFactory() {
		download = () -> {
			try {
				return call.call();
			} catch (Exception e) {
				throw UpdateException.class.cast(e);
			}
		};
	}
	
	public static void setLocation(URL link, String distDirectory) {
		call = () -> {
			try {
				return FileManagement.getDownload(link, distDirectory).call();
			} catch (Exception e) {
				throw new UpdateException(link, e);
			}
		};
	}
	
	public Downloadable getDownload() {
		return download;
	}
}
