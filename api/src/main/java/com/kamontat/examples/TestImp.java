package com.kamontat.examples;

import com.kamontat.exception.UpdateException;
import com.kamontat.implementation.GUpdater;
import com.kamontat.objects.Owner;
import com.kamontat.rawapi.Updatable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 21/Mar/2017 - 9:54 AM
 */
public class TestImp {
	public static void main(String[] args) throws UpdateException {
		Updatable update = new GUpdater(new Owner("kamontat", "CheckIDNumber"), "v1.0.0", 1);
		update.call();
		
		System.out.println(update.getTitle());
		System.out.println(update.getDescription());
		System.out.println(update.getVersion().getCurrentVersion());
		System.out.println(update.getVersion().getRemoteVersion());
		System.out.println(update.isLatest());
		if (!update.isLatest()) {
			System.out.println(update.getDownload().getSize());
			System.out.println(update.getDownload().getContentType());
			System.out.println(update.getDownload().download());
		}
	}
}
