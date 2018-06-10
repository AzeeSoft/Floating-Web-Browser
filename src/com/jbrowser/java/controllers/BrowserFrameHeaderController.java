package com.jbrowser.java.controllers;

import com.jbrowser.java.BrowserFrame;
import com.jfoenix.controls.JFXSpinner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserFrameHeaderController implements Initializable{

    @FXML
    public TextField addressBar;

    @FXML
    public Button nextBtn,backBtn;

    @FXML
    JFXSpinner loader;

    public BrowserFrame browserFrame;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        BrowserFrame mainFrame=new BrowserFrame("http://www.google.com", true, false);

/*
        addressBar.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                browser_.loadURL(addressBar.getText());
            }
        });*/

    }


    public void setBrowserFrame(BrowserFrame browserFrame) {
        this.browserFrame = browserFrame;
    }

    public void refreshForwardButton(){
        nextBtn.setDisable(!browserFrame.browser_.canGoForward());
    }

    public void refreshBackButton(){
        backBtn.setDisable(!browserFrame.browser_.canGoBack());
    }

    public void showLoading(){
        loader.setVisible(true);
    }

    public void hideLoading(){
        loader.setVisible(false);
    }

    public void showError(){

    }

    @FXML
    public void nextPage(){
        if(browserFrame.browser_.canGoForward())
            browserFrame.browser_.goForward();

//        refreshForwardButton();
    }

    @FXML
    public void lastPage(){
        if(browserFrame.browser_.canGoBack())
            browserFrame.browser_.goBack();

//        refreshForwardButton();
    }

    @FXML
    public void reload(){
        browserFrame.reloadPage();
    }

    @FXML
    public void goPressed(){
        browserFrame.loadFromAddressBar();
    }
}
