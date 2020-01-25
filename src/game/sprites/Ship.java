
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Ship extends SCObject implements KeyListener
{
    // Static Fields

    private static final float DELTA_A = (float) (1f / 24f);
    private static final float MAX_ACC = 1000f;

    // Constructors

    public Ship (String path, long chunkX, long chunkY, Vec pos)
    {
        this.x = (short) pos.x;
        this.y = (short) pos.y;
        this.rotation = 0;
        this.path     = path;
        this.texture  = 0;

        this.mass = 0.00001f;
        this.pos  = pos;
        this.vel  = Vec.ZERO;
        this.acc  = Vec.ZERO;
        this.dir  = Vec.UNIT_Y;

        BufferedImage texture = (BufferedImage) ResourceManager.loadResource (path).getValue ();

        if (texture != null) this.textureSheet = Images.buildTextureSheet (texture);
    }

    public Ship (String path)
    {
        this (path, 0, 0, Vec.ZERO);
    }

    // KeyListener Methods

    public void keyPressed (KeyEvent e)
    {
        switch (e.getKeyCode ())
        {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.rotation += Ship.DELTA_A + 1;
                this.rotation %= 1;
                this.dir = Mat.mult (Mat.rot ((float) (2 * Math.PI * this.rotation)), Vec.UNIT_Y);
                break;
            
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.rotation += - Ship.DELTA_A + 1;
                this.rotation %= 1;
                this.dir = Mat.mult (Mat.rot ((float) (2 * Math.PI * this.rotation)), Vec.UNIT_Y);
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.acc = Vec.scale (this.dir, 10000f);
                break;
            
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                this.acc = Vec.scale (this.dir, - 10000f);
                break;
        }
    }

    public void keyReleased (KeyEvent e)
    {
        switch (e.getKeyCode ())
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                this.acc = Vec.ZERO;
        }
    }

    public void keyTyped (KeyEvent e) { }
}