package com.jbrowser.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.prefs.Preferences;

/**
 * Created by aziz titu2 on 3/1/2016.
 */
public class Stools {
    private final static String PACKAGE_PREFIX = "/com/jbrowser/";
    private final static String FXML_PREFIX = PACKAGE_PREFIX + "res/layouts/";
    private final static String CSS_PREFIX = PACKAGE_PREFIX + "res/css/";
    private final static String ICONS_PREFIX = PACKAGE_PREFIX + "res/imgs/";


    public static class NodeWithController {
        public Object controller;
        public Node node;
    }

    public static <T> T loadFXML(Class classObj, String fxmlFileName) {
        try {
            fxmlFileName = fxmlFileName.replaceAll(".fxml", "");
            URL url = classObj.getResource(FXML_PREFIX + fxmlFileName + ".fxml");
            Stools.log(url.toString());
            return FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static NodeWithController loadFXMLWithController(Class classObj, String fxmlFileName) {
        NodeWithController nodeWithController = new NodeWithController();
        try {
            fxmlFileName = fxmlFileName.replaceAll(".fxml", "");
            URL url = classObj.getResource(FXML_PREFIX + fxmlFileName + ".fxml");
            Stools.log(url.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            nodeWithController.node = fxmlLoader.load();
            nodeWithController.controller = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeWithController;
    }

    public static URL loadCSSURL(Class classObj, String cssFileName) {
        return classObj.getResource(CSS_PREFIX + cssFileName);
    }


    public static Image loadImage(Class classObj, String imageName) {
        return new Image(classObj.getResourceAsStream(ICONS_PREFIX + imageName));
    }

    public static void log(String msg) {
        log("EejaLog", msg);
    }

    public static void log(String tag, String msg) {
        System.out.println(tag + " ==> " + msg);
        /*if (Main.posScreenController != null)
            Main.posScreenController.log(msg);*/
    }

    public static Dimension getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize;
    }

    public static Preferences getPreferences() {
        return Preferences.userRoot().node("azeePOS");
    }

    public static String getFormattedPrice(Double d) {
        return getFormattedPrice(d, 2);
    }

    public static String getFormattedPrice(Double d, int places) {
        /*String s=Double.toString(d);
        int dotPlace=s.indexOf(".");
        if(dotPlace==-1){
            s=s+".00";
        }else if(dotPlace==(s.length()-1)){
            s=s+"00";
        }else if(dotPlace==(s.length()-2)){
            s=s+"0";
        }else if(dotPlace<s.length()-3){
            s=s.substring(0,dotPlace+2);
        }*/

        /*if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        String s = Double.toString(bd.doubleValue());
        return s;   */

        return String.format("%.2f", d);

    }
/*

    public static void toast(Class classObj, String text) {
        toast(classObj, text, null);
    }

    public static void toast(Class classObj, String text,String bgColor) {

        NodeWithController nodeWithController = Stools.loadFXMLWithController(classObj, "toast.layouts");
        ToastController toastController = (ToastController) nodeWithController.controller;
        toastController.setText(text);
        if (bgColor != null)
            toastController.setBackground(bgColor);

        Toast toast = Toast.create().position(Pos.BOTTOM_RIGHT).hideAfter(Duration.millis(4000)).darkStyle();
        toast.graphic(nodeWithController.node);

        toastController.setToast(toast);
//        toastController.clickToDismiss();

        toast.show();

       */
/* Notifications notifications=Notifications.create().text(text).position(Pos.BOTTOM_RIGHT).hideAfter(Duration.millis(4000)).darkStyle();
        notifications.show();*//*


        Stools.log("Toasted",text);
    }
*/

    public static void animateWindow(JFrame jFrame, int from, int to, int orientation) {
        java.util.Timer timer = new Timer();
        CustomTimerTask customTimerTask = new CustomTimerTask() {

            int threshold = 25;
            int current = -1000;

            @Override
            public void run() {
                if (current == -1000)
                    current = from;

                if (current == to) {
                    this.cancel();
                    return;
                } else if (current < to) {
                    if (current + threshold > to)
                        current = to;
                    else
                        current += threshold;
                } else {
                    if (current - threshold < to)
                        current = to;
                    else
                        current -= threshold;
                }

                JFrame jFrame1 = (JFrame) window;
                if (orientation == 0) {
                    jFrame1.setLocation(current, jFrame1.getY());
                } else {
                    jFrame1.setLocation(jFrame1.getX(), current);
                }
            }
        };
        customTimerTask.from = from;
        customTimerTask.to = to;
        customTimerTask.window = jFrame;
        customTimerTask.orientation = orientation;

        timer.scheduleAtFixedRate(customTimerTask, 0, 10);
    }

    public static void animateWindow(JFrame jFrame, int fromX, int toX, int fromY, int toY) {
        java.util.Timer timer = new Timer();
        CustomTimerTask customTimerTask = new CustomTimerTask() {

            int threshold = 25;
            int currentX = -1000;
            int currentY = -1000;

            @Override
            public void run() {
                if (currentX == -1000)
                    currentX = from;
                if (currentY == -1000)
                    currentY = from2;

                if (currentX == to && currentY == to2) {
                    this.cancel();
                    return;
                } else {
                    if (currentX < to) {
                        if (currentX + threshold > to)
                            currentX = to;
                        else
                            currentX += threshold;
                    } else if(currentX>to) {
                        if (currentX - threshold < to)
                            currentX = to;
                        else
                            currentX -= threshold;
                    }

                    if (currentY < to2) {
                        if (currentY + threshold > to2)
                            currentY = to2;
                        else
                            currentY += threshold;
                    } else if(currentY>to2) {
                        if (currentY - threshold < to2)
                            currentY = to2;
                        else
                            currentY -= threshold;
                    }
                }


                JFrame jFrame1 = (JFrame) window;
                jFrame1.setLocation(currentX,currentY);
            }
        };

    customTimerTask.from=fromX;
    customTimerTask.to=toX;
    customTimerTask.from2=fromY;
    customTimerTask.to2=toY;
    customTimerTask.window=jFrame;

    timer.scheduleAtFixedRate(customTimerTask,0,10);
}

}
