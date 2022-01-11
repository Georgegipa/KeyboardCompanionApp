package manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tray {
    private AboutApp app;
    private MenuItem aboutItem;
    private MenuItem exitItem;
    private MenuItem disconnectedItem;
    private MenuItem connectedItem;
    private MenuItem unavaialableItem;
    private PopupMenu popup;
    private Menu ChangeStatusMenu;
    private Menu PortsMenu;
    private TrayIcon trayIcon;
    private SystemTray tray;
    private CheckboxMenuItem barIcon;
    public int currentProfile = 99;
    private String[] ports;
    public enum connectionStatus {UNKNOWN, UNAVAILABLE, CONNECTED, DISCONNECTED};
    private int timesCalled =0;
    private MinimizedWindow window;

    private void initComponents()
    {
        app = new AboutApp();
        barIcon = new CheckboxMenuItem("Display on taskbar");
        aboutItem = new MenuItem("About");
        exitItem = new MenuItem("Exit");
        PortsMenu = new Menu("Ports");
        ChangeStatusMenu = new Menu("Change Status");
        connectedItem = new MenuItem("Connected");
        unavaialableItem = new MenuItem("Unavailable");
        disconnectedItem = new MenuItem("Disconnected");
    }

    public Tray() {
        initComponents();
        ports = null;
        StaticUI();
        DynamicUI();
    }

    public Tray setPorts(String[] ports) {
        this.ports = ports;
        return this;
    }

    public void StaticUI(){
        popup = new PopupMenu();
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported is this os", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        trayIcon = new TrayIcon(ImageHelpers.createImage());
        trayIcon.setImageAutoSize(true);
        tray = SystemTray.getSystemTray();
        trayIcon.setToolTip(app.getName());

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(barIcon);
        popup.addSeparator();
        popup.add(PortsMenu);
        popup.addSeparator();
        popup.add(ChangeStatusMenu);
        ChangeStatusMenu.add(connectedItem);
        ChangeStatusMenu.add(disconnectedItem);
        ChangeStatusMenu.add(unavaialableItem);
        popup.addSeparator();
        popup.add(exitItem);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
            return;
        }
        barIcon.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED){
                    window = new MinimizedWindow();
                    window.prepareUI();
                } else {
                    window.dispose();
                }
            }
        });

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentProfile = new Random().nextInt(99);
                MenuItem item = (MenuItem) e.getSource();
                if ("Connected".equals(item.getLabel())) {
                    refreshIcon(connectionStatus.CONNECTED, currentProfile);
                } else if ("Disconnected".equals(item.getLabel())) {
                    refreshIcon(connectionStatus.DISCONNECTED, currentProfile);
                } else if ("Unavailable".equals(item.getLabel())) {
                    refreshIcon(connectionStatus.UNAVAILABLE, currentProfile);
                }
            }
        };

        connectedItem.addActionListener(listener);
        disconnectedItem.addActionListener(listener);
        unavaialableItem.addActionListener(listener);


        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutWindow().prepareUI();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    public void DynamicUI() {
        if(timesCalled>0)
        {
            PortsMenu.removeAll();
        }
        //generate dynamic ports menu
        if(ports != null)
        {
            CheckboxMenuItem[] items = new CheckboxMenuItem[ports.length];
            for(int i =0; i <ports.length;i++)
            {
                String portName = ports[i];
                items[i] = new CheckboxMenuItem(portName);
                PortsMenu.add(items[i]);
                items[i].addItemListener(new ItemListener() {
                    SerialThread thread = null;//= new SerialThread(portName);
                    public void itemStateChanged(ItemEvent e) {
                        int getState = e.getStateChange();
                        if( getState != ItemEvent.SELECTED){
                            thread.stop();
                        }
                        else {
                            thread = new SerialThread(portName);
                        }
                    }
                });
            }

        }
        else
        {
            MenuItem item = new MenuItem("No Serial Ports Detected");
            PortsMenu.add(item);
        }

        trayIcon.setPopupMenu(popup);
        timesCalled++;

    }

    private void refreshIcon(connectionStatus status, int profileNumber) {
        Color color = null;
        switch (status) {
            case CONNECTED:
                color = Color.GREEN;
                break;
            case DISCONNECTED:
                color = Color.RED;
                break;
            case UNAVAILABLE:
                color = Color.DARK_GRAY;
                break;
            case UNKNOWN:
                color = Color.GRAY;
                break;
        }
        try {
            BufferedImage editImg = ImageIO.read(Tray.class.getResource(app.getIconPath()));
            if (profileNumber >= 0)
                editImg = ImageHelpers.addNumberToIcon(editImg, profileNumber);
            ImageHelpers.addStatusBubble(editImg, color, 16);
            trayIcon.setImage(editImg);
            if(barIcon.getState())
            {
                window.updateContents(profileNumber,status.toString(),editImg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
