
import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;

abstract class Sprite
{
    // Fields

    protected short x;
    protected short y;

    protected float rotation;

    protected String path;
    protected int texture = 0;
    protected List<BufferedImage> textureSheet = new ArrayList<> ();

    // Methods

    public BufferedImage getNextTexture () {
        if (this.textureSheet.size () == 0) return null;

        this.texture = (this.texture + 1) % this.textureSheet.size ();

        return this.textureSheet.get (this.texture);
    }

    public String getPath ()
    {
        return this.path;
    }

    public short getX ()
    {
        return this.x;
    }

    public short getY ()
    {
        return this.y;
    }

    public float getRotation ()
    {
        return (float) (2 * Math.PI * this.rotation);
    }
}