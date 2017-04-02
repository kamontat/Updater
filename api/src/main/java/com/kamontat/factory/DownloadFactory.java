package com.kamontat.factory;

import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Downloadable;
import com.kamontat.utilities.URLManager;
import com.kamontat.utilities.URLReader;

import java.io.File;
import java.io.IOException;
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
	
	private DownloadFactory(URL link, Callable<String> action, long size, String content) {
		download = new Downloadable() {
			@Override
			public String download() throws UpdateException {
				try {
					return action.call();
				} catch (Exception e) {
					throw new UpdateException(link, e);
				}
			}
			
			@Override
			public long getSize() {
				return size;
			}
			
			@Override
			public String getContentType() {
				return content;
			}
		};
	}
	
	public static Downloadable setLocation(URL link, String distDirectory) {
		try {
			URLReader read = new URLReader(link, new File(distDirectory));
			long size = read.getTotalByte();
			String contentType = URLManager.getUrl(link).getConnection(URLManager.HTTP_CONNECTION).getContentType();
			Callable<String> call = () -> {
				read.setInput();
				while (read.read() != -1) {
					// System.out.println(read.getBytesRead());
					read.read();
				}
				return read.getOutputFile();
			};
			
			return new DownloadFactory(link, call, size, contentType).download;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
