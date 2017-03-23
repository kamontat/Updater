package com.kamontat.example;

import com.kamontat.api.Updater;
import com.kamontat.exception.UpdateException;
import com.kamontat.implementation.GUpdater;
import com.kamontat.objects.Owner;

/**
 * @author kamontat
 * @version 1.0
 * @since Thu 23/Mar/2017 - 11:19 PM
 */
public class APIUsage {
	public static void main(String[] args) throws UpdateException {
		Updater update = new GUpdater(new Owner("kamontat", "CheckIDNumber"), "v1.0.0", 1);
		System.out.println(update.call().getTitle());
	}
}
