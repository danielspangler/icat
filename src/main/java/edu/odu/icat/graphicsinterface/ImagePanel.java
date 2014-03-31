package edu.odu.icat.graphicsinterface;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by trueLove on 3/30/14.
 */
public class ImagePanel extends javax.swing.JPanel{

    private BufferedImage image;

    /*
     * Create a new image panel to be placed within a swing component
     * @param imageName the name of the image file including file extension.  Case sensitive.
     */
    public ImagePanel(String imageName) throws IOException{
        File imagePath = new File("src/main/resources/" + imageName);       //Get path to resources folder + image name
        try {
            image = ImageIO.read(imagePath);
        } catch (IOException ex) {
            throw new IOException();
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
