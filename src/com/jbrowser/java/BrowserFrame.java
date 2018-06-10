// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package com.jbrowser.java;

import com.jbrowser.java.controllers.BrowserFrameHeaderController;
import com.jbrowser.java.controllers.BrowserHeadController;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefLoadHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class BrowserFrame extends JFrame {
    private static final long serialVersionUID = -5570653778104813836L;
    private TextField address_;
    private final JFXPanel jfxPanel;
    private final CefApp cefApp_;
    private final CefClient client_;
    public  final CefBrowser browser_;
    private final Component browerUI_;

    BrowserFrameHeaderController browserFrameHeaderController;

    public BrowserFrame(String startURL, boolean useOSR, boolean isTransparent) {


        setUndecorated(true);
        setAlwaysOnTop(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setFocusable(true);
        setFocusableWindowState(true);

        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState()==Frame.ICONIFIED){
                    BrowserHeadController.thisController.hideFrame();
                    BrowserFrame.this.setState(Frame.NORMAL);
                }
            }
        });

        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED)
                    System.exit(0);
            }
        });
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;
        cefApp_ = CefApp.getInstance(settings);

        client_ = cefApp_.createClient();

        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browerUI_ = browser_.getUIComponent();

        client_.addLoadHandler(new CefLoadHandler() {
            @Override
            public void onLoadingStateChange(CefBrowser cefBrowser, boolean b, boolean b1, boolean b2) {

            }

            @Override
            public void onLoadStart(CefBrowser cefBrowser, int i) {
                browserFrameHeaderController.showLoading();
            }

            @Override
            public void onLoadEnd(CefBrowser cefBrowser, int i, int i1) {
                browserFrameHeaderController.hideLoading();
                address_.setText(cefBrowser.getURL());


//                browserFrameHeaderController.refreshForwardButton();
//                browserFrameHeaderController.refreshBackButton();
            }

            @Override
            public void onLoadError(CefBrowser cefBrowser, int i, ErrorCode errorCode, String s, String s1) {

            }
        });

        jfxPanel=new JFXPanel();

        AnchorPane header;

        Stools.NodeWithController nodeWithController=Stools.loadFXMLWithController(getClass(),"browserFrameHeader.fxml");

        header=(AnchorPane)nodeWithController.node;
        browserFrameHeaderController =(BrowserFrameHeaderController)nodeWithController.controller;
        browserFrameHeaderController.setBrowserFrame(this);

        address_ = browserFrameHeaderController.addressBar;
        address_.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {

                loadFromAddressBar();
            }
        });

        jfxPanel.setScene(new Scene(header));


        getContentPane().add(jfxPanel, BorderLayout.NORTH);
        getContentPane().add(jfxPanel, BorderLayout.NORTH);
        getContentPane().add(browerUI_, BorderLayout.CENTER);
        pack();

        Dimension dimension=Stools.getScreenSize();

        setLocation(0,50);
        setSize(dimension.width, dimension.height-50);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
//                dispose();
            }
        });
    }

    public void reloadPage(){
        browser_.reload();
    }

    public void forceReloadPage(){
        browser_.reloadIgnoreCache();
    }

    public void loadFromAddressBar(){
        browser_.loadURL(address_.getText());
    }

    public void loadPage(String s) {
        address_.setText(s);
        browser_.loadURL(s);
    }


    public static void main(String[] args) {
//    new BrowserFrame("http://www.google.com", OS.isLinux(), false);
    }

    public void exit(){
        cefApp_.dispose();
        try {
            setVisible(true);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
