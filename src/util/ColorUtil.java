
import java.awt.Color;

class ColorUtil
{
    /**
     * The scale color function takes a base color and interpolates
     * a color between black, white, and the base.
     * 
     * NOTE: an argument of 0 for the scalar will always yield black
     * and an argument of 1 for the scalar will always yield white
     * (with the alpha component preserved). Moreover, an argument
     * of 0.5 will return the base color. 
     * @param init the base color
     * @param scalar a number on [0,1]
     */
    public static Color scale (Color init, double scalar) {
        if (scalar < 0 || scalar > 1) return null;

        double s = (scalar < 0.5) ? 2 * scalar : 2 * scalar - 1;

        if (scalar < 0.5)
            return new Color  ((int) (init.getRed () * s)
                             , (int) (init.getGreen () * s)
                             , (int) (init.getBlue () * s)
                             , init.getAlpha ());
        else {
            double c = s * 255;
            return new Color ( (int) (init.getRed () * (1 - s) + s * 255)
                             , (int) (init.getGreen () * (1 - s) + s * 255)
                             , (int) (init.getBlue () * (1 - s) + s * 255)
                             , init.getAlpha ());
        }
    }
}