package com.kamontat.object;

import com.fasterxml.jackson.databind.JsonNode;
import com.kamontat.Converter;
import com.kamontat.config.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Latest Release <br>
 * Usage: <br>
 * &emsp; getting data inside that release by {@link #type(ReleaseTitle, Class)} <br>
 * &emsp; also getting asset inside that release by {@link #getAsset(int)}
 */
public class Release {
	/**
	 * release title of the json
	 */
	public enum ReleaseTitle {
		URL,
		ASSETS_URL,
		UPLOAD_URL,
		HTML_URL,
		ID,
		/**
		 * you can assume this to remoteVersion name
		 */
		TAG_NAME,
		/**
		 * you can assume this to title of remoteVersion
		 */
		NAME,
		CREATE_AT,
		UPDATE_AT,
		TARBALL_URL,
		ZIPBALL_URL,
		/**
		 * you can assume this to description, <b>HTML Format</b>
		 */
		BODY;
	}
	
	/**
	 * node will be null if no latest release
	 */
	private JsonNode node;
	
	/**
	 * use this method if not latest release
	 *
	 * @return empty release (you can check isEmpty by {@link #isEmpty()} method)
	 */
	public static Release getEmptyRelease() {
		return new Release(null);
	}
	
	/**
	 * constructor to setting node
	 *
	 * @param node
	 * 		the json node
	 */
	public Release(JsonNode node) {
		this.node = node;
	}
	
	/**
	 * change text to json in form of "{ "text": "xxx" }"
	 *
	 * @param text
	 * 		some text
	 * @return String in form of json
	 */
	private String toJSON(String text) {
		return "{ \n\"text\": \"" + text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n") + "\"\n}";
	}
	
	/**
	 * check is release empty or not.
	 *
	 * @return true if empty release.
	 */
	public boolean isEmpty() {
		return node == null;
	}
	
	/**
	 * convert {@link BufferedReader} to {@link String}
	 *
	 * @param reader
	 * 		buffer to converting
	 * @return String that content in buffer
	 */
	private String toString(BufferedReader reader) {
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException ignore) {
		}
		return sb.toString();
	}
	
	/**
	 * get data by title of the json and cast to className by parameter <br>
	 * If you pass {@link ReleaseTitle#BODY}, it's will automatically convert to html form.
	 *
	 * @param title
	 * 		getting the title of json
	 * @param className
	 * 		class that want to cast (simple pass by String.class, Object.class, etc.)
	 * @param <T>
	 * 		T class to cast
	 * @return object in form of class that pass in parameter, or try cast <code>empty string</code>("") to <code>className</code> parameter
	 * @see Converter
	 */
	@SuppressWarnings("unchecked")
	public <T> T type(ReleaseTitle title, Class<T> className) {
		if (isEmpty()) return className.cast(Configuration.NOT_FOUND_RELEASE_MESSAGE);
		
		String output = node.get(title.name().toLowerCase(Locale.ENGLISH)).asText();
		
		// if body must convert markdown syntax to html syntax
		if (title == ReleaseTitle.BODY) {
			try {
				output = Converter.by(Converter.Type.MD2HTML).convert(output).toString();
				output = "<html>".concat(output).concat("</html>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return className.cast(output);
	}
	
	/**
	 * since in github you can add asset more than 1 file, so you need to pass parameter file index that you want to use
	 *
	 * @param number
	 * 		index of asset
	 * @return {@link Assets} - getting asset with the index; <br>
	 * BUT if no latest release or no asset occurred it's will return empty asset;
	 */
	public Assets getAsset(int number) {
		if (this.node == null) return Assets.getEmptyAsset();
		
		JsonNode assetsNode = this.node.get("assets");
		if (assetsNode == null) return Assets.getEmptyAsset();
		return new Assets(assetsNode.get(number));
	}
}
