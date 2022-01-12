package manager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MinimizedWindow extends JFrame {
    private String labelContents;
    public  MinimizedWindow(){
        super();
        AboutApp app = new AboutApp();
        labelContents = "<html>"
                + "<p>Current Profile:?</p>"
                + "<p>Connection Status:UNKNOWN"
                +"</html>";
        this.setIconImage(ImageHelpers.createImage());
    }

    public void updateContents(int profile, String status,BufferedImage img)
    {
        labelContents = "<html>"
                + "<p>Current Profile:"+profile+"</p>"
                + "<p>Connection Status:"+status+"</p>"
                +"</html>";
        prepareUI();
        this.setIconImage(img);
    }

    public void prepareUI() {
        JLabel infoLbl = new JLabel(labelContents);
        infoLbl.setText(labelContents);
        this.add(infoLbl);

        //setup the frame
        this.setState(Frame.ICONIFIED);
        this.setSize(350, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Info-"+ AboutApp.getName());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
