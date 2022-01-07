package manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tray {
    private final String IconPath = "keypad.png" ;
    private MenuItem aboutItem;
    private MenuItem exitItem;
    private MenuItem disconnectedItem;
    private MenuItem connectedItem;
    private MenuItem unavaialableItem;
    private PopupMenu popup;
    private Menu ChangeStatusMenu;
    private TrayIcon trayIcon;
    private SystemTray tray;
    public int currentProfile = 99;

    public Tray(){
        aboutItem = new MenuItem("About");
        exitItem = new MenuItem("Exit");
        ChangeStatusMenu = new Menu("Change Status");
        connectedItem = new MenuItem("Connected");
        unavaialableItem = new MenuItem("Unavailable");
        disconnectedItem = new MenuItem("Disconnected");
    }

    public void PrepareUI()
    {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null,"SystemTray is not supported is this os","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage(IconPath, "tray icon"));
        trayIcon.setImageAutoSize(true);
        tray = SystemTray.getSystemTray();

        //Add components to popup menu
        popup.add(aboutItem);
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
                MenuItem item = (MenuItem)e.getSource();
                if ("Connected".equals(item.getLabel())) {
                    refreshIcon(Color.GREEN,currentProfile);
                } else if ("Disconnected".equals(item.getLabel())) {
                    refreshIcon(Color.RED,currentProfile);
                } else if ("Unavailable".equals(item.getLabel())) {
                    refreshIcon(Color.GRAY,currentProfile);
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
                JOptionPane.showMessageDialog(null,"This dialog box is run from System Tray");
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"This dialog box is run from the About menu item");
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    public  void refreshIcon (Color color, int profileNumber)
    {
        try {
            BufferedImage editImg = ImageIO.read(Tray.class.getResource(IconPath));
            if(profileNumber >= 0 )
                addProfileNumber(editImg,profileNumber);
            addStatusBubble(editImg,color,16);
        }
        catch (IOException e )
        {
        }
    }

    private void addStatusBubble(BufferedImage editImg, Color color , int size){
        Graphics2D g2d = editImg.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(editImg.getWidth() - size, 0, size, size);
        g2d.dispose();
        trayIcon.setImage(editImg);
    }

    /**
     * Display the current profile over the trayIcon
     * @param editImg The trayIcon image to overlay the given number
     * @param profileNumber The number which is going to be displayed
     */
    private void addProfileNumber(BufferedImage editImg, int profileNumber) {
            Graphics2D g2d = editImg.createGraphics();
            g2d.setPaint(Color.WHITE);
            g2d.setFont(new Font("Serif", Font.BOLD, 65));
            String s = Integer.toString(profileNumber);
            g2d.drawString(s, 0, editImg.getHeight()-5);
            g2d.dispose();
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = Tray.class.getResource(path);
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

}
