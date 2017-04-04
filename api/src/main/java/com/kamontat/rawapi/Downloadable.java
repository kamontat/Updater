package com.kamontat.rawapi;

import com.kamontat.annotation.Nullable;
import com.kamontat.exception.UpdateException;
import com.kamontat.utilities.URLReader;

import java.util.concurrent.Callable;

/**
 * get more information or download file <br>
 * Implemented at {@link com.kamontat.factory.DownloadFactory}
 *
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:38 PM
 */
public interface Downloadable extends Callable<String> {
	/**
	 * get reader
	 *
	 * @return {@link URLReader} for reading data
	 */
	URLReader getReader();
	
	/**
	 * try to download something (this is long long method) <br>
	 * You might need to execute in in background <br>
	 * The action you can use method in {@link #getDefaultAction(URLReader)} (already implemented in {@link com.kamontat.factory.DownloadFactory})
	 *
	 * @param action
	 * 		action while downloading, or {@code null} if you don't want to do anything
	 * @return result location
	 * @throws UpdateException
	 * 		Error occurred when try to download
	 */
	String download(@Nullable Runnable action) throws UpdateException;
	
	/**
	 * get download size
	 *
	 * @return size in Byte (you can convert in {@link com.kamontat.utilities.SizeUtil})
	 */
	long getSize();
	
	/**
	 * get get of download file (learn more at {@link com.kamontat.constance.ContentType})
	 *
	 * @return get in String format
	 */
	String getContentType();
	
	/**
	 * get name of download file
	 *
	 * @return String file name
	 */
	String getName();
	
	/**
	 * same thing with {@link #download(Runnable)} method and using {@link #getDefaultAction(URLReader)} as parameter as well.<br>
	 * <p>
	 * Shouldn't override more
	 *
	 * @return result location
	 * @throws Exception
	 * 		when error occurred
	 */
	@Override
	default String call() throws Exception {
		return download(getDefaultAction(getReader()));
	}
	
	/**
	 * just print reader byte, can use in {@link Downloadable#download(Runnable)} method
	 *
	 * @param r
	 * 		reader {@link URLReader}
	 * @return runnable
	 */
	static Runnable getDefaultAction(URLReader r) {
		return () -> System.out.println(r.getBytesRead());
	}
}
