package manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tray {
    private AboutApp app;
    private MenuItem aboutItem;
    private MenuItem testItem;
    private MenuItem exitItem;
    private MenuItem disconnectedItem;
    private MenuItem connectedItem;
    private MenuItem unavaialableItem;
    private PopupMenu popup;
    private Menu ChangeStatusMenu;
    private TrayIcon trayIcon;
    private SystemTray tray;
    public int currentProfile = 99;

    public enum connectionStatus {UNKNOWN, UNAVAILABLE, CONNECTED, DISCONNECTED,}

    ;

    public Tray() {
        app = new AboutApp();
        aboutItem = new MenuItem("About");
        testItem = new MenuItem("Test");
        exitItem = new MenuItem("Exit");
        ChangeStatusMenu = new Menu("Change Status");
        connectedItem = new MenuItem("Connected");
        unavaialableItem = new MenuItem("Unavailable");
        disconnectedItem = new MenuItem("Disconnected");
    }

    public void PrepareUI() {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported is this os", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        popup = new PopupMenu();
        trayIcon = new TrayIcon(ImageHelpers.createImage());
        trayIcon.setImageAutoSize(true);
        tray = SystemTray.getSystemTray();

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(testItem);
        popup.addSeparator();
        popup.add(ChangeStatusMenu);
        ChangeStatusMenu.add(connectedItem);
        ChangeStatusMenu.add(disconnectedItem);
        ChangeStatusMenu.add(unavaialableItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

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

        trayIcon.setToolTip("Hello from UKP Manager");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "This dialog box is run from System Tray");
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutWindow().prepareUI();
            }
        });

        testItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TestJFrame().prepareUI();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    public void refreshIcon(connectionStatus status, int profileNumber) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
