package manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class TestJFrame extends JFrame {
    private final AboutApp app;
    private JButton updateICON;

    public  TestJFrame(){
        super();
        updateICON = new JButton("Update Icon");
        app = new AboutApp();

    }

    private void updateIcon(BufferedImage img)
    {
        this.setIconImage(img);
    }

    public void prepareUI() {

        this.add(updateICON);//προσθήκη του label στο Jframe
        updateICON.addActionListener(e -> {
            try {
                updateIcon(ImageHelpers.addNumberToIcon(ImageHelpers.pathToBufferedImage(app.getIconPath()),new Random().nextInt(99)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //setup the frame
        this.setSize(350, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setTitle("Test-"+ AboutApp.getName());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ImageHelpers.createImage());
    }
}
