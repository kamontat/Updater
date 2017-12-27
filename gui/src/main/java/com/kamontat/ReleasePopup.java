package com.kamontat;

import com.kamontat.abstractui.AbstractUI;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.rawapi.Updatable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The popup that contains update description and downloader link.
 *
 * @author kamontat
 * @version 2.0
 * @since Tue 14/Mar/2017 - 2:27 PM
 */
public class ReleasePopup extends AbstractUI {
    
    public ReleasePopup(Updatable update) throws UpdateException {
        super(update);
    }
    
    public ReleasePopup(UpdaterFactory update) throws UpdateException {
        super(update);
    }
    
    /**
     * before starting loading
     */
    public void preExecute() {
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    /**
     * after starting loading
     */
    public void postExecute() {
        dialog.setCursor(Cursor.getDefaultCursor());
    }
    
    public void postExecute(Object obj) {
        dialog.setCursor(Cursor.getDefaultCursor());
        JOptionPane.showMessageDialog(super.window, obj.toString(), "Message!", JOptionPane.PLAIN_MESSAGE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Download");
    }
}