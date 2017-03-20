package com.kamontat.code.config;

import com.kamontat.code.gui.ReleasePopup;
import com.kamontat.code.model.github.Release;
import com.kamontat.code.utilities.MessageUtil;

import java.awt.event.ActionEvent;
import java.io.Serializable;

/**
 * Before you use anything in this library, You might need to config some variable inside this class first.
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 8:03 PM
 */
public class Configuration implements Serializable {
	/**
	 * serial number
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * The popup title when user already up to date
	 *
	 * @see MessageUtil#UP_TO_DATE()
	 */
	public static String UP_TO_DATE_TITLE = "Every thing up to date";
	/**
	 * The popup message when user already up to date
	 *
	 * @see MessageUtil#UP_TO_DATE()
	 */
	// public static String UP_TO_DATE_MESSAGE = String.format("current version (%s) is up to date, \nremote version(%s)", Updater.getCurrentVersion(), Updater.getRemoteVersion());
	
	/**
	 * The button title inside {@link ReleasePopup} class
	 *
	 * @see ReleasePopup#setPanel()
	 */
	public static String DOWNLOAD_BUTTON = "Update!";
	/**
	 * The type of data inside Popup, The body message to describe the version
	 *
	 * @see ReleasePopup#setPanel()
	 */
	public static String INPUT_TYPE = "text/html";
	/**
	 * set be false, if don't want to ask user to open new program when the new program are already downloaded.
	 *
	 * @see ReleasePopup#actionPerformed(ActionEvent)
	 */
	public static boolean HAS_ALERT = true;
	/**
	 * The Alert title (usually use when you need your user to tell you that he/she want to open new program or not)
	 *
	 * @see ReleasePopup#actionPerformed(ActionEvent)
	 */
	public static String ALERT_COMPLETE_TITLE = "Download complete!";
	/**
	 * The Alert message (usually use when you need your user to tell you that he/she want to open new program or not)
	 *
	 * @see ReleasePopup#actionPerformed(ActionEvent)
	 */
	public static String ALERT_COMPLETE_MESSAGE = "Do you want to open it?";
	
	/**
	 * use to sent the message back when latest release not found
	 *
	 * @see Release#type(Release.ReleaseTitle, Class)
	 */
	public static String NOT_FOUND_RELEASE_MESSAGE = "Latest Release: not found";
	
	/**
	 * auto checking update program when open the program
	 */
	public static boolean isAutoUpdate = false;
}