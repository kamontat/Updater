package com.kamontat.example;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.implementation.GUpdater;
import com.kamontat.object.Owner;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:54 AM
 */
public class TestImp {
	public static void main(String[] args) throws UpdateException {
		// URLsUtil.getUrl(URLsUtil.Protocol.HTTPS, "github.com/kamontat/CheckIDNumber/releases/download/v3.0/CheckIDNumber.jar").getConnection().getInputStream()
		Updater update = new GUpdater(new Owner("kamontat", "CheckIDNumber"), "v1.0.0", 1);
		System.out.println(update.call().getTitle());
	}
}
