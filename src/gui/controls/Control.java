
import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

abstract class Control implements MouseListener, KeyListener
{
    // Classes, Interfaces, Enums

    public interface Action
    {
        void execute ();
    }

    // Fields

    protected int x;
    protected int y;
    protected int leftX;
    protected int rightX;
    protected int topY;
    protected int bottomY;

    protected int width;
    protected int height;

    protected boolean visible;
    protected boolean disabled;

    protected Action action;

    private List<ControlListener> listeners = new ArrayList<> ();

    // Methods

    public void addControlListener (ControlListener cl)
    {
        listeners.add (cl);
    }

    public void updateControlListeners (ControlEvent e)
    {
        for (ControlListener cl : listeners) cl.controlOccurred (e);
    }

    /**
     * Returns whether the specified position is over the button.
     * 
     * @param x the x position
     * @param y the y position
     */
    public boolean over (int x, int y)
    {
        return this.leftX <= x && x <= this.rightX &&
               this.topY <= y && y <= this.bottomY;
    }

    /**
     * Sets the internal visibility of this object
     *
     * @param visible the visibility
     */
    public void setVisible (boolean visible)
    {
        this.visible = visible;
    }

    /**
     * Sets the internal disabled state of this object
     *
     * @param disable the disabled state
     */
    public void setDisabled (boolean disable)
    {
        this.disabled = disable;
    }

    /**
     * Empty paint method
     *
     * @param g the graphics environment
     */
    public void paint (Graphics g) { }

    // KeyListener Methods

    public void keyPressed (KeyEvent e) { }

    public void keyReleased (KeyEvent e) { }

    public void keyTyped (KeyEvent e) { }

    // MouseListener Methods

    public void mouseExited (MouseEvent e) { }

    public void mouseEntered (MouseEvent e) { }

    public void mousePressed (MouseEvent e) { }

    public void mouseReleased (MouseEvent e) { }

    public void mouseClicked (MouseEvent e) { }
}