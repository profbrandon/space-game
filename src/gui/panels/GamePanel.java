
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class GamePanel extends GPanel implements GameListener, KeyListener
{
    public static final int CHUNK_SIZE = 2400;
    public static final String NAME = "Game";

    private int tick = 0;
    private int centerX;
    private int centerY;
    private long chunkX;
    private long chunkY;
    private float speed;
    private int width;
    private int height;
    private SpaceCowboys parent;
    private List<Sprite> sprites = new ArrayList<> ();

    private Chunk[][] chunks;

    private Game game;

    public GamePanel (int width, int height, SpaceCowboys parent)
    {
        this.setPreferredSize (new Dimension (width, height));
        this.setBackground (Color.BLACK);
        this.repaint ();

        this.width  = width;
        this.height = height;
        this.parent = parent;
        this.chunks = null;
        this.speed  = 0f;

        this.loadResources ();
    }

    // JPanel Methods

    public void paint (Graphics g)
    {
        super.paint (g);

        // Draw Chunks

        if (chunks != null)
        {
            for (int x = -1; x <= 1; ++x)
            {
                for (int y = -1; y <= 1; ++y)
                {
                    Chunk chunk = chunks[x + 1][y + 1];

                    if (centerX > -800 && x == -1) continue;
                    if (centerX < 800 && x == 1) continue;
                    if (centerY > -600 && y == -1) continue;
                    if (centerY < 600 && y == 1) continue;

                    Draw.drawImage (
                          g
                        , Chunk.TEXTURES.get (chunk.texture)
                        , this.width / 2 - (centerX - x * CHUNK_SIZE)
                        , this.height / 2 + (centerY - y * CHUNK_SIZE)
                        , this.width
                        , this.height
                        , 2.0f
                        , 0
                    );
                }
            }
        }

        // Draw Sprites
        for (Sprite s : sprites)
        {
            BufferedImage texture = s.getNextTexture ();

            final int x = (s.getX () - centerX) + this.width / 2;
            final int y = this.height / 2 - (s.getY () - centerY);

            if (texture == null) {
                g.setColor (new Color (255, 0, 230));
                g.fillRect (x - 15, y - 15, 31, 31);
                continue;
            }

            Draw.drawImage (g, texture, x, y, this.width, this.height, 1.0f, s.getRotation ());
        }

        g.setColor (Color.WHITE);
        g.drawString ("cx: " + NumUtil.formatLong (this.chunkX, 16), 10, 10);
        g.drawString ("cy: " + NumUtil.formatLong (this.chunkY, 16), 90, 10);
        g.drawString ("px: " + this.centerX, 10, 25);
        g.drawString ("py: " + this.centerY, 90, 25);
        g.drawString ("speed: " + this.speed, 10, 40);
    }

    // GameListener Methods

    public void tick (GameState state)
    {
        this.tick    = state.tick;
        this.sprites = state.sprites;
        this.chunks  = state.chunks;
        this.centerX = state.playerX;
        this.centerY = state.playerY;
        this.chunkX  = state.chunkX;
        this.chunkY  = state.chunkY;
        this.speed   = state.speed;

        repaint ();
    }

    // GPanel Methods

    public void loadResources () { }

    public void receiveFocus (String message)
    {
        if (message != null)
        {
            this.game = new Game (message);
            this.game.addListener (this);
        }
        
        this.game.play ();
    }

    public void loseFocus ()
    {
        this.game.pause ();
    }

    // KeyListener Methods

    public void keyReleased (KeyEvent e)
    {
        this.game.keyReleased (e);
    }

    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode () == KeyEvent.VK_ESCAPE)
        {
            this.game.pause ();
            this.game.save ();
            this.parent.switchPanel ("Main Menu", null);
            return;
        }

        this.game.keyPressed (e);
    }

    public void keyTyped (KeyEvent e)
    {
        this.game.keyTyped (e);
    }
}