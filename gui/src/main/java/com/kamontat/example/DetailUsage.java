package com.kamontat.example;

import com.kamontat.ReleasePopup;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
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
		//		DetailPanel p = new DetailPanel(DetailPanel.TextType.HTML, "What **I** can *do* `this`?");
		//		JFrame f = new JFrame("Test page");
		//		f.setContentPane(JxAndSw.toSwing(p.initScene()));
		//		f.pack();
		//		f.setVisible(true);
		//		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		Owner owner = new Owner("kamontat", "CheckIDNumber");
		Updatable update = new GUpdater(owner, "v1.0.0", 1);
		
		UpdaterFactory factory = UpdaterFactory.setUpdater(update);
		
		factory.checkRelease();
		
		ReleasePopup p = new ReleasePopup(factory);
		
		p.run();
	}
}
