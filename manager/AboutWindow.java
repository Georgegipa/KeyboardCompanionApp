package manager;

import javax.swing.*;

public class AboutWindow extends JFrame {
    private AboutApp app;
    public  AboutWindow(){
        super();
        app = new AboutApp();
    }
    public void prepareUI() {
        JLabel aboutLbl = new JLabel(
                        "<html>"
                        +"<img src=\""
                        + AboutWindow.class.getResource(app.getIconPath())
                        + "\" width='64' height='64' >"
                        + "<p>Developed by:"+app.getDeveloper()+"</p>"
                        + "<p>Build:"+app.getBuild()
                        + "<p>Version:"+app.getVersion()+"</p>"+"</html>");
        this.add(aboutLbl);//προσθήκη του label στο Jframe

        //setup the frame
        this.setSize(350, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("About-"+app.getName());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(ImageHelpers.createImage());
    }
}
