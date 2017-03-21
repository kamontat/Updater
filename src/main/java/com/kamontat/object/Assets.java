package com.kamontat.object;

import com.fasterxml.jackson.databind.JsonNode;

import java.text.DecimalFormat;
import java.util.*;

/**
 * asset that content in {@link Release} <br>
 * and same with {@link Release}, you can getting data inside json by method {@link #get(AssetTitle)}.
 */
public class Assets {
	/**
	 * asset title of the json
	 */
	public enum AssetTitle {
		URL,
		ID,
		NAME,
		CONTENT_TYPE,
		SIZE,
		DOWNLOAD_COUNT,
		CREATE_AT,
		UPDATE_AT,
		BROWSER_DOWNLOAD_URL;
	}
	
	/**
	 * node will be null if no asset occurred
	 */
	private JsonNode node;
	
	/**
	 * use this method if not asset
	 */
	protected static Assets getEmptyAsset() {
		return new Assets(null);
	}
	
	/**
	 * constructor to setting node.
	 *
	 * @param node
	 * 		the json node.
	 */
	protected Assets(JsonNode node) {
		this.node = node;
	}
	
	/**
	 * convert size
	 *
	 * @param size
	 * 		in form of <code>B</code>
	 * @return String in format (#,###.##)
	 */
	private String convertSize(long size) {
		if (size <= 0) return "0";
		final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	/**
	 * get data by title of the json.
	 *
	 * @param title
	 * 		json title
	 * @return data String in title, or empty string("") if no asset occurred
	 */
	public String get(AssetTitle title) {
		if (node == null) return "";
		String output = node.get(title.name().toLowerCase(Locale.ENGLISH)).asText();
		if (title == AssetTitle.SIZE) output = convertSize(Long.parseLong(output));
		return output;
	}
}
