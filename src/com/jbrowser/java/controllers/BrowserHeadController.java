package com.jbrowser.java.controllers;

import com.jbrowser.java.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.cef.OS;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class BrowserHeadController implements Initializable {

    public static BrowserHeadController thisController;


    @FXML
    Button head;

    @FXML
    ContextMenu options;


    BrowserFrame browserFrame;
    BrowserHead browserHead;


    double firstScreenX, firstScreenY,firstHeadX,firstHeadY;
    boolean dragged=false;
    double prevX, prevY;


    public void setBrowserHead(BrowserHead browserHead) {
        this.browserHead = browserHead;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        thisController=this;

        browserFrame=new BrowserFrame("http://www.google.com", OS.isLinux(),false);
        browserFrame.loadPage("http://www.google.com");

        head.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton()  == MouseButton.PRIMARY) {
                    firstScreenX = event.getScreenX();
                    firstScreenY = event.getScreenY();
                    firstHeadX = browserHead.getX();
                    firstHeadY = browserHead.getY();
                }
            }
        });


        head.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!BrowserManager.opened && event.getButton() == MouseButton.PRIMARY) {
                    Stools.log("Dragging... ");
                    dragged=true;

                    double diffX = event.getScreenX() - firstScreenX;
                    double diffY = event.getScreenY() - firstScreenY;

                    if (browserHead != null) {
                        browserHead.setLocation((int)(firstHeadX + diffX),(int)(firstHeadY + diffY));
//                        browserHead.setY(firstHeadY + diffY);
                    }
                }
                Stools.log(event.getEventType().getName());
            }
        });

        head.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stools.log("Mouse Exit");
                if(dragged){
                    dragged=false;
                    if (!BrowserManager.opened) {
                        Dimension dimension=Stools.getScreenSize();
                        if(browserHead.getX()<dimension.getWidth()/2) {
//                            browserHead.setX(-10);
                            Stools.animateWindow(browserHead,browserHead.getX(),-10,0);
                        }else{
//                            browserHead.setX(dimension.getWidth()-30);
                            Stools.animateWindow(browserHead,browserHead.getX(),(int)(dimension.getWidth()-30),0);
                        }

                        if(browserHead.getY()<0){
//                            browserHead.setY(0);
                            Stools.animateWindow(browserHead,browserHead.getY(),0,1);
                        }else if(browserHead.getY()>dimension.getHeight()-40){
//                            browserHead.setY(dimension.getHeight()-40);
                            Stools.animateWindow(browserHead,browserHead.getY(),(int)(dimension.getHeight()-40),1);
                        }
                    }
                }
            }
        });

/*
        head.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                Stools.log("Drag Over");
            }
        });

        head.setOnMouseDragExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stools.log("Drag Exit");
                if (BrowserManager.opened) {
                    browserHead.setX(10);
                    browserHead.setY(10);
                }
            }
        });*/

        head.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(Math.abs(firstScreenX-event.getScreenX())>3 || Math.abs(firstScreenY-event.getScreenY())>3){
                    return;
                }

                Stools.log("Clicked");
                if (!BrowserManager.opened) {
                    showFrame();
                } else {
                    hideFrame();
                }
            }
        });

    }

    public void showFrame(){
        prevX = browserHead.getX();
        prevY = browserHead.getY();
//        browserHead.setX(5);
//        browserHead.setY(5);

        Dimension dimension=Stools.getScreenSize();

        int x;

        if(browserHead.getX()<dimension.getWidth()/2){
            x=5;
        }else{
            x=dimension.width-45;
        }

        Stools.log(""+x);

        Stools.animateWindow(browserHead,browserHead.getX(),x,browserHead.getY(),5);


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                browserFrame.setVisible(true);
            }
        });


        AppInit.backgroundWindow.setSize(dimension.width,dimension.height);
        BrowserManager.opened = true;
    }

    public void hideFrame(){
//        browserHead.setX(prevX);
//        browserHead.setY(prevY);

        Stools.animateWindow(browserHead,browserHead.getX(),(int)prevX,browserHead.getY(),(int)prevY);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                browserFrame.setVisible(false);
            }
        });
        AppInit.backgroundWindow.setSize(0,0);
        BrowserManager.opened = false;
    }

    @FXML
    public void closeApp(){

        if(!BrowserManager.opened){
            browserHead.setOpacity(0);
            browserFrame.setOpacity(0);
            showFrame();
            java.util.Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    initAppClose();
                }
            },2000);
        }else{
            initAppClose();
        }
    }

    public void initAppClose(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Platform.exit();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        browserFrame.setVisible(true);
                        browserFrame.exit();
                        System.exit(0);
                    }
                });
            }
        });
    }
}
