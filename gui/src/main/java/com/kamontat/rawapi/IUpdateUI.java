package com.kamontat.rawapi;

import com.kamontat.factory.UpdaterFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 30/Apr/2017 - 4:57 PM
 */
public interface IUpdateUI {
	
	void setUpdatable(Updatable updatable);
	
	void setUpdateFactory(UpdaterFactory factory);
	
	IUpdateUI setView(Window window);
	
	IUpdateUI setName(String name);
	
	IUpdateUI setLocation(Point point);
	
	void setNorthPanel(JPanel root);
	
	void setCenterPanel(JPanel root);
	
	void setSouthPanel(JPanel root);
	
	default JPanel getRootPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		setNorthPanel(panel);
		setCenterPanel(panel);
		setSouthPanel(panel);
		return panel;
	}
}
