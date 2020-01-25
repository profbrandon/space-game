
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;

import java.util.Map;
import java.util.HashMap;

class SpaceCowboys extends JFrame
{
    // Static Fields

    public static final int DEFAULT_WIDTH  = 800;
    public static final int DEFAULT_HEIGHT = 600;

    // Fields

    private Map<String, GPanel> panels = new HashMap<> ();

    private GamePanel     gamePanel = new GamePanel (DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
    private MainMenuPanel mainMenu  = new MainMenuPanel (DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
    private WorldLoaderPanel worldPanel = new WorldLoaderPanel (DEFAULT_WIDTH, DEFAULT_HEIGHT, this);

    // Constructors

    public SpaceCowboys ()
    {
        super ("Space Cowboys");

        this.setVisible (true);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize (new Dimension (DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setMaximumSize (new Dimension (DEFAULT_WIDTH, DEFAULT_HEIGHT));

        this.getContentPane ().add (mainMenu);

        this.pack ();
        this.repaint ();

        this.init ();
    }

    // Methods

    /**
     * Initializes the frame.
     */
    public void init ()
    {
        panels.put (GamePanel.NAME, this.gamePanel);
        panels.put (MainMenuPanel.NAME, this.mainMenu);
        panels.put (WorldLoaderPanel.NAME, this.worldPanel);

        this.addKeyListener (this.gamePanel);
    }

    /**
     * Switches from the current panel to the panel
     * specified by the given name.
     * @param name the panel name
     */
    public void switchPanel (String name, String message)
    {
        if (this.panels.containsKey (name))
        {
            ((GPanel) this.getContentPane ().getComponent (0)).loseFocus ();
            this.getContentPane ().remove (0);
            this.getContentPane ().add (this.panels.get (name));
            this.panels.get (name).receiveFocus (message);
            this.revalidate ();

            this.setMinimumSize (new Dimension (DEFAULT_WIDTH, DEFAULT_HEIGHT));
            this.setMaximumSize (new Dimension (DEFAULT_WIDTH, DEFAULT_HEIGHT));
        }
        else
        {
            System.out.println ("Error: Attempted to switch to a panel which does not exist");
        }
    }

    // Static Methods

    public static void main (String args[])
    {
        ResourceManager.setRoot ("../data/");

        SwingUtilities.invokeLater (() -> {
            SpaceCowboys game = new SpaceCowboys ();
        });
    }
}