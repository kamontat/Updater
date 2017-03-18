package com.kamontat.code.utilities;

import com.kamontat.code.config.Configuration;

import javax.swing.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Tue 14/Mar/2017 - 3:39 PM
 */
public class MessageUtil {
	public static void UP_TO_DATE() {
		JOptionPane.showMessageDialog(null, Configuration.UP_TO_DATE_MESSAGE, Configuration.UP_TO_DATE_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
