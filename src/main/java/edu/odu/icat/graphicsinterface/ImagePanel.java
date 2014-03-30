package edu.odu.icat.graphicsinterface;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/**
 * Created by trueLove on 3/30/14.
 */
public class ImagePanel extends javax.swing.JPanel{

    private BufferedImage image;

    public ImagePanel(String imageName) {
        try {
            image = ImageIO.read(new File("/Users/trueLove/Documents/GIT/ICAT/src/main/resources/logo.png"));  //relative path next
        } catch (IOException ex) {
            System.out.println("could not load image");
        }
    }

    public int getHeight()
    {
        return image.getHeight();
    }

    public int getWidth()
    {
        return image.getWidth();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
