package manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ImageHelpers {
    private static final AboutApp app= new AboutApp();

    public static Image createImage() {
        URL imageURL = Tray.class.getResource(app.getIconPath());
        if (imageURL == null) {
            System.err.println("Icon not found!" );
            return null;
        } else {
            return (new ImageIcon(imageURL, "icon")).getImage();
        }
    }

    /** Return the BufferedImage corresponding to imgPath.*/
    public static BufferedImage pathToBufferedImage(String imgPath) throws IOException {
        return ImageIO.read(Objects.requireNonNull(ImageHelpers.class.getResource(imgPath)));
    }

    /** Overlay the given number over the given image.     */
    public static BufferedImage addNumberToIcon(BufferedImage editImg , int Number) {
        Graphics2D g2d = editImg.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.setFont(new Font("Serif", Font.BOLD, 65));
        String s ="" ;
        if(Number <= 9)
            s = " ";
        s += Integer.toString(Number);
        g2d.drawString(s, 0, editImg.getHeight()-5);
        g2d.dispose();
        return editImg;
    }

    /** Overlay a bubble of the given size and color in the upper rigth corner of the given image.*/
    public static BufferedImage addStatusBubble(BufferedImage editImg, Color color , int size){
        Graphics2D g2d = editImg.createGraphics();
        g2d.setColor(color);
        g2d.fillOval(editImg.getWidth() - size, 0, size, size);
        g2d.dispose();
        return editImg;
    }
}
