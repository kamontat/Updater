package com.kamontat.object;

import com.fasterxml.jackson.databind.JsonNode;
import com.kamontat.constants.SizeUnitType;
import com.kamontat.utilities.SizeUtil;

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
		DOWNLOAD_URL;
	}
	
	/**
	 * node will be null if no asset occurred
	 */
	private JsonNode node;
	
	/**
	 * use this method if not asset
	 */
	static Assets getEmptyAsset() {
		System.err.println("empty asset");
		return new Assets(null);
	}
	
	/**
	 * constructor to setting node.
	 *
	 * @param node
	 * 		the json node.
	 */
	Assets(JsonNode node) {
		this.node = node;
	}
	
	/**
	 * get data by title of the json.
	 *
	 * @param title
	 * 		json title
	 * @return data String in title, or empty string("") if no asset occurred
	 */
	public String get(AssetTitle title) {
		String output = node.get(title.name().toLowerCase(Locale.ENGLISH)).asText();
		if (title == AssetTitle.SIZE) output = SizeUtil.toMinimumByte(Long.parseLong(output), SizeUnitType.NON_SI);
		return output;
	}
}
