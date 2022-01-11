package manager;

import javax.swing.*;

public class GuiThread extends Thread {
    private final Tray tray;
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
        lastNumberOfPorts= SerialScanner.portsSum();
        numberOfPorts = lastNumberOfPorts;
        tray.setPorts(SerialScanner.getPortNames());
        tray.DynamicUI();
    }

    public void run(){
        while (true) {
            numberOfPorts = SerialScanner.portsSum();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Thread error");
            }
            if (numberOfPorts != lastNumberOfPorts) {
                tray.setPorts(SerialScanner.getPortNames());
                tray.DynamicUI();
                lastNumberOfPorts = numberOfPorts;
            }
        }

    }
}
