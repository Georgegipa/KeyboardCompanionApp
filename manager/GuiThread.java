package manager;

import javax.swing.*;

public class GuiThread extends Thread {
    private final Tray tray;
    private final SerialScanner SS;
    private int numberOfPorts, lastNumberOfPorts;
    public GuiThread(){
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.err.println("UI error");
        }
        tray = new Tray();
        SS = new SerialScanner(false);
        lastNumberOfPorts= SS.portsSum();
        numberOfPorts = lastNumberOfPorts;
        tray.setPorts(SS.getPortNames());
        tray.DynamicUI();
    }

    public void run(){
        while (true) {
            numberOfPorts = SS.portsSum();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Thread error");
            }
            if (numberOfPorts != lastNumberOfPorts) {
                tray.setPorts(SS.getPortNames());
                tray.DynamicUI();
                lastNumberOfPorts = numberOfPorts;
            }
        }

    }
}
