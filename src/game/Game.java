
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.io.File;

class Game implements ActionListener, KeyListener
{
    // Fields

    private int tick;

    private World world;

    private List<Sprite> sprites;
    private List<SCObject> objects;
    private List<KeyListener> keyListeners = new ArrayList<> ();

    private List<GameListener> listeners = new ArrayList<> ();
    private Timer timer = new Timer (100, this);

    // Constructor

    public Game (String worldName)
    {
        File file = new File ("../data/worlds/" + worldName);

        if (file.exists ())
        {
            this.world = World.loadWorld (worldName);
        }
        else
        {
            this.world = new World (worldName);
        }

        this.tick = 0;
        this.sprites = new ArrayList<> ();
        this.objects = new ArrayList<> ();

        Ship player = this.world.getPlayer ();

        this.sprites.add (player);
        this.objects.add (player);
        this.keyListeners.add (player);
    }

    // Methods

    private void callListeners ()
    {
        Ship player = this.world.getPlayer ();

        Chunk[][] chunks = this.world.getLocale (player.getChunkCoord ());

        GameState state = new GameState (
              this.tick
            , this.sprites
            , chunks
            , player.getX ()
            , player.getY ()
            , player.getChunkX ()
            , player.getChunkY ()
            , player.getVel ().len ()
        );

        for (GameListener listener : listeners) listener.tick (state);
    }

    public void addListener (GameListener listener)
    {
        this.listeners.add (listener);
    }

    public void play ()
    {
        this.timer.start ();
    }

    public void pause ()
    {
        this.timer.stop ();
    }

    public void save ()
    {
        World.writeWorld (this.world);
    }

    private void update ()
    {
        Map<SCObject, Vec> positions = new HashMap<> ();
        Map<SCObject, Vec> velocities = new HashMap<> ();
        Map<SCObject, Vec> accelerations = new HashMap<> ();

        for (SCObject obj : objects)
        {
            Vec force = Vec.ZERO;

            for (SCObject obj2 : objects)
            {
                if (obj != obj2) force = Vec.add (force, SCObject.gravForce (obj, obj2));
            }

            positions.put (obj, obj.getNewPos ());
            velocities.put (obj, obj.getNewVel ());
            accelerations.put (obj, obj.getNewAcc (force));
        }

        for (SCObject obj : objects)
        {
            obj.setPos (positions.get (obj));
            obj.setVel (velocities.get (obj));
            obj.setAcc (accelerations.get (obj));
        }
    }

    // ActionListener Methods

    public void actionPerformed (ActionEvent e)
    {
        ++this.tick;

        this.update ();

        this.callListeners ();
    }

    // KeyListener Methods

    public void keyReleased (KeyEvent e)
    {
        for (KeyListener l : keyListeners)
            l.keyReleased (e);
    }

    public void keyPressed (KeyEvent e)
    {
        for (KeyListener l : keyListeners)
            l.keyPressed (e);
    }

    public void keyTyped (KeyEvent e)
    {
        for (KeyListener l : keyListeners)
            l.keyTyped (e);
    }
}