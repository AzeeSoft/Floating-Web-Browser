package com.jbrowser.java;

import org.cef.OS;

/**
 * Created by aziz titu2 on 2/2/2016.
 */
public class BrowserInit {

    public BrowserInit(){

    }

    public static void main(String[] args){
        new BrowserFrame("http://www.google.com", OS.isLinux(),false);

    }
}
