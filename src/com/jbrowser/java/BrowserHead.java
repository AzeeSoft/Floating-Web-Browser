package com.jbrowser.java;

import com.jbrowser.java.controllers.BrowserHeadController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aziz titu2 on 5/4/2016.
 */
public class BrowserHead extends JFrame {
    public Stools.NodeWithController nodeWithController;

    public BrowserHead() {
        nodeWithController = Stools.loadFXMLWithController(getClass(), "browserHead.fxml");

        setUndecorated(true);
        /*
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);*/
        this.setAlwaysOnTop(true);
        setType(Type.UTILITY);

        setBackground(new Color(0,0,0,0));

        JFXPanel jfxPanel=new JFXPanel();
        Scene scene=new Scene((AnchorPane) nodeWithController.node);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        jfxPanel.setScene(scene);
        jfxPanel.setBackground(new Color(0,0,0,0));
        jfxPanel.setOpaque(false);

        getContentPane().add(jfxPanel, BorderLayout.CENTER);
        pack();
        setLocation(-10,100);

        BrowserHeadController browserHeadController = (BrowserHeadController) nodeWithController.controller;
        browserHeadController.setBrowserHead(this);
    }

    public void setX(double x){
        setLocation((int)x,getY());
    }

    public void setY(double y){
        setLocation(getX(),(int)y);
    }

    public void setX(int x){
        setLocation(x,getY());
    }

    public void setY(int y){
        setLocation(getX(),y);
    }
}
