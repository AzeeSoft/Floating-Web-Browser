package com.jbrowser.java;

import com.jbrowser.java.controllers.BrowserHeadController;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by aziz titu2 on 5/4/2016.
 */
public class AppInit extends Application {
    public static BrowserHead browserHead;
    public static JWindow backgroundWindow;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        backgroundWindow=new JWindow();
        backgroundWindow.setAlwaysOnTop(true);
        backgroundWindow.getContentPane().setBackground(Color.BLACK);
        backgroundWindow.getContentPane().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (BrowserManager.opened) {
                    BrowserHeadController browserHeadController=(BrowserHeadController)browserHead.nodeWithController.controller;
                    browserHeadController.hideFrame();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        backgroundWindow.setOpacity(0.8f);
        backgroundWindow.setBounds(0,0,0,0);
        backgroundWindow.setVisible(true);

        browserHead=new BrowserHead();
        browserHead.setVisible(true);
    }
}
