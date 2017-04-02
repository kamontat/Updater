package com.kamontat.rawapi;

import com.kamontat.exception.UpdateException;

import java.util.concurrent.Callable;

/**
 * @author kamontat
 * @version 1.0
 * @since Mon 20/Mar/2017 - 10:38 PM
 */
public interface Downloadable extends Callable<String> {
	/**
	 * try to download something (this is long long method) <br>
	 * You might need to execute in in background
	 *
	 * @return result location
	 * @throws UpdateException
	 * 		Error occurred when try to download
	 */
	String download() throws UpdateException;
	
	long getSize();
	
	String getContentType();
	
	/**
	 * same thing with {@link #download()} method
	 *
	 * @return result location
	 * @throws Exception
	 * 		when error occurred
	 */
	@Override
	default String call() throws Exception {
		return download();
	}
}
