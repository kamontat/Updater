package com.kamontat.abstractui;

import com.kamontat.component.DetailPanel;
import com.kamontat.exception.UpdateException;
import com.kamontat.factory.UpdaterFactory;
import com.kamontat.rawapi.IUpdateUI;
import com.kamontat.rawapi.Updatable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author kamontat
 * @version 1.0
 * @since Sun 30/Apr/2017 - 5:06 PM
 */
public abstract class AbstractUI implements IUpdateUI, Runnable, ActionListener {
    private static final String DEFAULT_NAME = "Update Page";
    private static final Point DEFAULT_POINT = new Point(0, 0);
    protected JDialog dialog;
    protected Window window;
    private String name;
    
    protected UpdaterFactory factory;
    private Point point;
    
    /**
     * Constructor to create popup <br>
     *
     * @param update
     *         The updater
     */
    public AbstractUI(Updatable update) throws UpdateException {
        this(UpdaterFactory.setUpdater(update));
    }
    
    public AbstractUI(UpdaterFactory update) throws UpdateException {
        setUpdateFactory(update);
        update.checkRelease();
    }
    
    @Override
    public void setUpdatable(Updatable updatable) {
        this.factory = UpdaterFactory.setUpdater(updatable);
    }
    
    @Override
    public void setUpdateFactory(UpdaterFactory factory) {
        this.factory = factory;
    }
    
    @Override
    public IUpdateUI setView(Window window) {
        this.window = window;
        return this;
    }
    
    @Override
    public IUpdateUI setName(String name) {
        this.name = name;
        return this;
    }
    
    @Override
    public IUpdateUI setLocation(Point point) {
        this.point = point;
        return this;
    }
    
    @Override
    public void run() {
        if (Objects.nonNull(name)) dialog = new JDialog(window, name);
        else dialog = new JDialog(window, DEFAULT_NAME);
        
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setContentPane(getRootPanel());
        if (Objects.nonNull(point)) dialog.setLocation(point);
        dialog.pack();
        dialog.setVisible(true);
    }
    
    @Override
    public void setNorthPanel(JPanel root) {
        JPanel p = new JPanel(new BorderLayout());
        if (!factory.isLatest()) {
            p.add(new JLabel(String.format("current: %-6s", factory.getCurrentVersion()), SwingConstants.CENTER), BorderLayout.NORTH);
            p.add(new JLabel(String.format("newest : %-6s", factory.getRemoteVersion()), SwingConstants.CENTER), BorderLayout.SOUTH);
        } else {
            p.add(new JLabel("Up to date (" + factory.getCurrentVersion() + ")", SwingConstants.CENTER), BorderLayout.CENTER);
        }
        root.add(p, BorderLayout.NORTH);
    }
    
    @Override
    public void setCenterPanel(JPanel root) {
        root.add(new DetailPanel(factory.getDescription(), DetailPanel.TextType.HTML).init(), BorderLayout.CENTER);
    }
    
    @Override
    public void setSouthPanel(JPanel root) {
        if (!factory.isLatest()) {
            JButton button = new JButton("update!");
            button.addActionListener(this);
            root.add(button, BorderLayout.SOUTH);
        }
    }
}
