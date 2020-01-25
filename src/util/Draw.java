
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;

import java.awt.geom.AffineTransform;

import java.awt.Rectangle;
import java.awt.Point;

class Draw
{
    /**
     * Wraps the given image around the x-axis
     * @param g the graphics environment
     * @param img the image
     * @param x the upper left x coordinate
     * @param y the upper left y coordinate
     * @param width the width of the screen
     * @param height the height of the screen
     */
    public static void wrapImageX (Graphics g, BufferedImage img, int x, int y, int width, int height)
    {
        g.drawImage (img, x, y, width, height, null);

        if (x > 0) g.drawImage (img, x - width, y, width, height, null);
        if (x < 0) g.drawImage (img, x + width, y, width, height, null);
    }

    /**
     * Draws the given image rotated to the specified angle,
     * scaled to the correct size, and centered at the given
     * coordinate.
     * 
     * @param g the graphics environment
     * @param img the image
     * 
     * @param x the center x coordinate of the image
     * @param y the center y coordinate of the image
     *
     * @param sWidth the width of the screen
     * @param sHeight the height of the screen
     *
     * @param scale the scaling factor for the image
     * @param angle the angle to rotate the image by
     */
    public static void drawImage (Graphics g, BufferedImage img, int x, int y, int sWidth, int sHeight, float scale, float angle)
    {
        BufferedImage temp;

        // Optimize for specific angles
        if (Math.abs (angle % (2 * Math.PI)) >= 0.001) 
        {
            temp = Draw.rotate (img, angle);
        }
        else 
        {
            temp = img;
        }

        final int width  = (int) (scale * temp.getWidth ());
        final int height = (int) (scale * temp.getHeight ());

        g.drawImage (temp, x - (width / 2), y - (width / 2), width, height, null);
    }

    /**
     * Rotates an image by the given angle.
     * @param img the image to rotate
     * @param angle the specified angle
     * 
     * @return the rotated image
     */
    public static BufferedImage rotate (BufferedImage img, float angle)
    {
        final double sin = Math.abs (Math.sin (angle));
        final double cos = Math.abs (Math.cos (angle));

        int w = (int) Math.floor (img.getWidth () * cos + img.getHeight () * sin);
        int h = (int) Math.floor (img.getHeight () * cos + img.getWidth () * sin);

        AffineTransform at = new AffineTransform ();

        at.translate (w / 2, h / 2);
        at.rotate (- angle, 0, 0);
        at.translate (-img.getWidth () / 2, -img.getHeight () / 2);
        
        final AffineTransformOp rotateOp = new AffineTransformOp (at, AffineTransformOp.TYPE_BILINEAR);
        final BufferedImage rotatedImage = new BufferedImage (w, h, img.getType ());
        
        rotateOp.filter (img, rotatedImage);

        return rotatedImage;
    }
}