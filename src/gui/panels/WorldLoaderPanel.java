
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;

import java.io.File;

class WorldLoaderPanel extends GPanel implements ControlListener
{
    // Fields

    public static final String NAME = "World Loader";

    private SpaceCowboys parent;
    private int width;
    private int height;

    private final ScrollMenu menu = new ScrollMenu (400, 250, 600, 460, new Color (60, 60, 60), () -> {
        this.select.setDisabled (false);
        this.deleteWorld.setDisabled (false);
    });

    private final Button select = new Button ("SELECT", 400, 500, 200, 20, new Color (60, 60, 60), () -> {
        String title = this.menu.getSelected ();

        if (title != null)
        {
            this.parent.switchPanel ("Game", title);
        }
    });

    private final Button deleteWorld = new Button ("DELETE", 400, 530, 200, 20, new Color (60, 60, 60), () -> {
        String title = this.menu.getSelected ();

        if (title != null)
        {
            World.deleteWorld (title);
            this.menu.removeItem (title);
        }
    });

    private final Button newWorld = new Button ("CREATE", 400, 560, 200, 20, new Color (60, 60, 60), () -> {
        
        this.parent.switchPanel ("Game", "World 2");
    });

    // Constructors

    public WorldLoaderPanel (int width, int height, SpaceCowboys parent)
    {
        this.setBackground (Color.BLACK);

        this.width = width;
        this.height = height;
        this.parent = parent;

        this.addMouseMotionListener (menu);

        this.addMouseListener (menu);
        this.addMouseListener (select);
        this.addMouseListener (deleteWorld);
        this.addMouseListener (newWorld);

        this.menu.addControlListener (this);
        this.select.addControlListener (this);
        this.deleteWorld.addControlListener (this);
        this.newWorld.addControlListener (this);

        this.select.setDisabled (true);
        this.deleteWorld.setDisabled (true);

        List<String> names = WorldLoaderPanel.getWorldNames ();

        for (String name : names) this.menu.addItem (name, "");
    }

    // JPanel Methods

    @Override
    public void paint (Graphics g)
    {
        super.paint (g);

        this.select.paint (g);
        this.deleteWorld.paint (g);
        this.newWorld.paint (g);
        this.menu.paint (g);     // Must come after to prevent clipping
    }

    // ControlListener Methods

    public void controlOccurred (ControlEvent e)
    {
        this.repaint ();

        if (e.type == ControlEvent.Type.SCROLL_ITEM_DESELECT)
        {
            this.select.setDisabled (true);
            this.deleteWorld.setDisabled (true);
        }
    }

    // Static Methods

    public static List<String> getWorldNames ()
    {
        try
        {
            File file = new File ("../data/worlds/worlds.txt");

            if (file.exists ())
            {
                Scanner scanner = new Scanner (file);

                List<String> lines = new ArrayList<> ();

                while (scanner.hasNext ()) lines.add (scanner.nextLine ());

                return lines;
            }
        }
        catch (Exception e)
        {
            System.out.println ("Failed to Load World Name File: " + e);
        }

        return new ArrayList<> ();
    }

    // GPanel Methods

    public void loseFocus ()
    {
        this.select.reset ();
        this.deleteWorld.reset ();
        this.newWorld.reset ();
    }
}