package com.kamontat.code.utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 2:59 PM
 */
public class DesktopAction {
	private static Desktop desktop;
	private boolean isSupport;
	
	public static DesktopAction get() {
		return new DesktopAction();
	}
	
	private DesktopAction() {
		isSupport = Desktop.isDesktopSupported();
		if (isSupport) desktop = Desktop.getDesktop();
	}
	
	/**
	 * open the file
	 *
	 * @param file
	 * 		the file
	 */
	public void open(File file) {
		if (!isSupport) return;
		if (file == null) return;
		if (desktop.isSupported(Desktop.Action.OPEN)) {
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * open the link in browser
	 *
	 * @param uri
	 * 		link
	 */
	public void browse(URI uri) {
		if (!isSupport) return;
		if (uri == null) return;
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * open the link in browser
	 *
	 * @param url
	 * 		url link
	 */
	public void browse(URL url) {
		if (!isSupport) return;
		if (url == null) return;
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url.toURI());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * open file in editor
	 *
	 * @param file
	 * 		the file
	 */
	public void edit(File file) {
		if (!isSupport) return;
		if (file == null) return;
		if (desktop.isSupported(Desktop.Action.EDIT)) {
			try {
				desktop.edit(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
