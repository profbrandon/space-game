
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

/**
 * Class to represent a stylized button
 */
class Button extends Control
{
    // Fields

    private String text;
    private int textWidth;

    private Color color;
    private Color normalColor;
    private Color clickedColor;

    private boolean clicked;

    // Constructors

    /**
     * Constructs a button with the specified attributes.
     * @param text the button text
     * @param x the button's center x position
     * @param y the button's center y position
     * @param width the width of the button
     * @param height the height of the button
     * @param color the main color of the button
     * @param action the action to be executed on a click
     */
    public Button (String text, int x, int y, int width, int height, Color color, Action action)
    {
        this.text    = text;
        this.x       = x;
        this.y       = y;
        this.width   = width;
        this.height  = height;
        this.color   = color;
        this.visible = true;
        this.clicked = false;
        this.disabled = false;

        this.normalColor  = color;
        this.clickedColor = ColorUtil.scale (color, 0.75);

        this.leftX   = this.x - (this.width / 2);
        this.rightX  = this.leftX + this.width;
        this.topY    = this.y - (this.height / 2);
        this.bottomY = this.topY + this.height;

        this.textWidth = (text.length () == 0) ? 0
                       : text.length () * 4 + (text.length () - 1);

        this.action  = action;
    }

    // Methods

    /**
     * @param g the graphics enviornment to draw the button on
     */
    public void paint (Graphics g)
    {
        if (!this.visible) return;

        float dim = 1f;

        if (this.disabled) dim = 0.6f;

        Color originalColor = g.getColor ();
        Font originalFont   = g.getFont ();

        // Border
        g.setColor (ColorUtil.scale (this.color, 0.75 * dim));
        g.drawRoundRect (this.leftX, this.topY, this.width, this.height, 2, 2);

        // Background
        g.setColor (this.color);
        g.fillRect (this.leftX + 1, this.topY + 1, this.width - 1, this.height - 1);

        // Text
        g.setFont (new Font ("Consolas", Font.PLAIN, 9));
        g.setColor (ColorUtil.scale (this.color, 0.9 * dim));
        g.drawString (this.text, this.x - this.textWidth / 2, this.y + 3);

        // Clean Up
        g.setColor (originalColor);
        g.setFont (originalFont);
    }

    public void reset ()
    {
        this.color   = this.normalColor;
        this.clicked = false;
    }

    // MouseListener Methods

    public void mouseReleased (MouseEvent e)
    {
        if (clicked)
        {
            this.color   = this.normalColor;
            this.clicked = false;
            this.updateControlListeners (ControlEvent.NULL);
        }
    }

    public void mousePressed (MouseEvent e)
    {
        if (!this.disabled && this.visible && !this.clicked && this.over (e.getX (), e.getY ()))
        {
            this.color   = this.clickedColor;
            this.clicked = true;
            this.updateControlListeners (ControlEvent.NULL);

            this.action.execute ();
        }
    }
}