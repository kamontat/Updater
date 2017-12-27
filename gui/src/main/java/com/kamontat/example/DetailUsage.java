package com.kamontat.example;

import com.kamontat.ReleasePopup;
import com.kamontat.exception.UpdateException;
import com.kamontat.implementation.GUpdater;
import com.kamontat.objects.Owner;
import com.kamontat.rawapi.Updatable;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 04/Apr/2017 - 10:14 PM
 */
public class DetailUsage {
	public static void main(String[] args) throws UpdateException {
		Owner owner = new Owner("kamontat", "CheckIDNumber");
		Updatable update = new GUpdater(owner, "v2.0", 1);
		
		ReleasePopup p = new ReleasePopup(update);
		p.run();
	}
}
