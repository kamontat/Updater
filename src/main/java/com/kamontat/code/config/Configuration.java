package main.java.com.kamontat.code.config;

import main.java.com.kamontat.code.popup.DescriptionPopup;
import main.java.com.kamontat.code.server.Updater;
import main.java.com.kamontat.code.server.github.Release;
import main.java.com.kamontat.code.utilities.MessageUtil;

/**
 * Before you use anything in this library, You might need to config some variable inside this class first.
 *
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 8:03 PM
 */
public class Configuration {
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
	public static String UP_TO_DATE_MESSAGE = String.format("current version (%s) is up to date, \nremote version(%s)", Updater.currentVersion, Updater.remoteVersion);
	
	/**
	 * The button title inside {@link DescriptionPopup} class
	 *
	 * @see DescriptionPopup#setPanel()
	 */
	public static String DOWNLOAD_BUTTON = "Download!";
	/**
	 * The type of data inside Popup, The body message to describe the version
	 *
	 * @see DescriptionPopup#setPanel()
	 */
	public static String INPUT_TYPE = "text/html";
	/**
	 * set be false, if don't want to ask user to open new program when the new program are already downloaded.
	 *
	 * @see DescriptionPopup#downloadAction()
	 */
	public static boolean HAS_ALERT = true;
	/**
	 * The Alert title (usually use when you need your user to tell you that he/she want to open new program or not)
	 *
	 * @see DescriptionPopup#downloadAction()
	 */
	public static String ALERT_COMPLETE_TITLE = "Download complete!";
	/**
	 * The Alert message (usually use when you need your user to tell you that he/she want to open new program or not)
	 *
	 * @see DescriptionPopup#downloadAction()
	 */
	public static String ALERT_COMPLETE_MESSAGE = "Do you want to open it?";
	
	/**
	 * use to sent the message back when latest release not found
	 *
	 * @see main.java.com.kamontat.code.server.github.Release#type(Release.ReleaseTitle, Class)
	 */
	public static String NOT_FOUND_RELEASE_MESSAGE = "Latest Release: not found";
}