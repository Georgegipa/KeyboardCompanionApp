package manager;

import javax.swing.*;

public class GuiThread extends Thread {

    public GuiThread(){
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.err.println("UI error");
        }
        Tray tray = new Tray();
        tray.PrepareUI();
    }

//    public void run(){
//        tray.refreshIcon();
//    }
}
