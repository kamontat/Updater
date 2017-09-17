package com.kamontat.factory;

import com.kamontat.annotation.Nullable;
import com.kamontat.exception.UpdateException;
import com.kamontat.rawapi.Downloadable;
import com.kamontat.utilities.URLManager;
import com.kamontat.utilities.URLReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * factory of {@link Downloadable} for more easy to use <br>
 * To assign you need to know 2 thing
 * <ul><li>First is {@link URL} of download file</li><li>Second is {@link String} Path of direction file in place</li></ul>
 * And that you easy create {@link Downloadable} by {@link #createDownloadable(URL, String)} method
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 10:16 AM
 */
public class DownloadFactory {
	private Downloadable download;
	
	/**
	 * create downloadable here.
	 *
	 * @param link
	 * 		download file link
	 * @param file
	 * 		file of result wit read data finish
	 */
	private DownloadFactory(URL link, File file) {
		download = new Downloadable() {
			private URLReader reader;
			
			@Override
			public URLReader getReader() throws UpdateException {
				if (reader == null) try {
					reader = new URLReader(link, file);
				} catch (IOException e) {
					throw new UpdateException(link, e);
				}
				return reader;
			}
			
			@Override
			public String download(@Nullable Runnable action) throws UpdateException, IOException {
				try {
					return createAction(getReader(), action).call();
				} catch (Exception e) {
					throw new UpdateException(link, e);
				}
			}
			
			@Override
			public long getSize() {
				try {
					return getReader().getTotalByte();
				} catch (UpdateException e) {
					e.printStackTrace();
					return 0;
				}
			}
			
			@Override
			public String getContentType() {
				return URLManager.getUrl(link).getConnection(URLManager.HTTP_CONNECTION).getContentType();
			}
			
			@Override
			public String getName() {
				return URLManager.getUrl(link).getURLFilename();
			}
		};
	}
	
	/**
	 * Create downloadable by <b>link</b> and <b>direction path</b>. <br>
	 * Using {@link URLReader} to read to URL
	 *
	 * @param link
	 * 		File link (remotely)
	 * @param distDirectory
	 * 		Directory path (locally)
	 * @return {@link Downloadable}
	 */
	public static Downloadable createDownloadable(URL link, String distDirectory) {
		File f = new File(distDirectory);
		return new DownloadFactory(link, f).download;
	}
	
	/**
	 * download action
	 *
	 * @param read
	 * 		{@link URLReader} reader
	 * @param run
	 * 		doing when reading
	 * @return download action what return path of direction file
	 */
	private static Callable<String> createAction(URLReader read, @Nullable Runnable run) {
		return () -> {
			read.setInput();
			read.setOutput();
			// optional: // TODO 9/17/2017 AD 16:34 implement with reader bg
			// ReaderBackground background = new ReaderBackground(read);
			while (read.read() != -1) {
				if (run != null) run.run();
			}
			return read.getOutputFile();
		};
	}
}
