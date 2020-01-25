
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

class MainMenuPanel extends GPanel implements ControlListener, ActionListener
{
    public static final String NAME = "Main Menu";
    private static int FRAME_COUNT = 3;

    private float offset = 0;
    private Timer timer  = new Timer (10, this);
    private BufferedImage background1;
    private BufferedImage background2;
    private BufferedImage background3;
    private SpaceCowboys parent;

    private Button start = new Button ("PLAY", 400, 400, 200, 20, new Color (60, 60, 60), () -> {
        parent.switchPanel ("World Loader", null);
    });
    private Button help = new Button ("HELP", 400, 440, 200, 20, new Color (60, 60, 60), () -> {
        parent.switchPanel ("Help Menu", null);
    });
    private Button quit = new Button ("QUIT", 400, 480, 200, 20, new Color (60, 60, 60), () -> {

    });

    // Constructor

    public MainMenuPanel (int width, int height, SpaceCowboys parent)
    {
        this.parent = parent;

        this.loadResources ();

        this.setPreferredSize (new Dimension (width, height));
        this.setBackground (Color.PINK);

        this.addMouseListener (start);
        this.addMouseListener (help);
        this.addMouseListener (quit);
        
        this.start.addControlListener (this);
        this.help.addControlListener (this);
        this.quit.addControlListener (this);

        this.timer.start ();
    }

    // Methods

    public void loadResources ()
    {
        this.background1 = (BufferedImage) ResourceManager.loadResource ("textures/main-menu-1.png").getValue ();
        this.background2 = (BufferedImage) ResourceManager.loadResource ("textures/main-menu-2.png").getValue ();
        this.background3 = (BufferedImage) ResourceManager.loadResource ("textures/main-menu-3.png").getValue ();
    }

    // JPanel Methods

    public void paint (Graphics g)
    {
        super.paint (g);

        Draw.wrapImageX (g, this.background1, (int) offset, 0, FRAME_COUNT * this.getWidth (), this.getHeight ());
        Draw.wrapImageX (g, this.background2, (int) (offset * 2) % (FRAME_COUNT * this.getWidth ()),
                         0, FRAME_COUNT * this.getWidth (), this.getHeight ());
        Draw.wrapImageX (g, this.background3, (int) (offset * 3) % (FRAME_COUNT * this.getWidth ()),
                         0, FRAME_COUNT * this.getWidth (), this.getHeight ());

        start.paint (g);
        help.paint (g);
        quit.paint (g);
    }

    // ControlListener Methods

    public void controlOccurred (ControlEvent e)
    {
        this.repaint ();
    }

    // ActionListener Methods

    public void actionPerformed (ActionEvent e)
    {
        this.offset += 0.05;
        this.offset %= FRAME_COUNT * this.getWidth ();
        this.repaint ();
    }

    // GPanel Methods

    public void receiveFocus (String message) 
    {
        this.timer.start ();
    }

    public void loseFocus ()
    {
        this.timer.stop ();

        this.start.reset ();
        this.help.reset ();
        this.quit.reset ();
    }
}