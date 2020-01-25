
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

class ScrollMenuItem extends Control
{
    // Fields

    private String title;
    private String text;
    private Color color;
    private Color unselectedColor;
    private Color selectedColor;

    private boolean selected;

    // Constructor

    /**
     * Creates a scroll menu item.
     * 
     * @param title the title string
     * @param text the text string
     * @param x the leftmost x coordinate
     * @param y the topmost y coordinate
     * @param width the width of the item
     * @param height the height of the item
     * @param color the color of the item
     */
    public ScrollMenuItem (String title, String text, int x, int y, int width, int height, Color color)
    {
        this.x = x;
        this.y = y;

        this.width  = width;
        this.height = height;

        this.leftX   = this.x;
        this.rightX  = this.leftX + this.width;
        this.topY    = this.y;
        this.bottomY = this.topY + this.height;

        this.color = color;
        this.unselectedColor = color;
        this.selectedColor = ColorUtil.scale (color, 0.75);

        this.title = title;
        this.text  = text;

        this.disabled = false;
        this.visible  = true;
        this.selected = false;
    }

    // Methods

    /**
     * Paints the item in the graphics with the specified
     * vertical offset.
     * 
     * @param g the graphics to draw in
     * @param offset the vertical offset
     */
    public void paint (Graphics g, int offset)
    {
        if (!this.visible) return;
        
        float dim = 1f;

        if (this.disabled) dim = 0.6f;

        Color originalColor = g.getColor ();
        Font  originalFont  = g.getFont ();

        // Border
        g.setColor (ColorUtil.scale (this.color, 0.6 * dim));
        g.drawRect (this.x, this.y + offset, this.width, this.height);

        // Background
        g.setColor (ColorUtil.scale (this.color, 0.2 * dim));
        g.fillRect (this.x + 1, this.y + 1 + offset, this.width - 1, this.height - 1);

        // Text
        g.setColor (ColorUtil.scale (this.color, 0.9 * dim));
        g.setFont (new Font ("Consolas", Font.PLAIN, 9));

        String[] lines = this.text.split ("\n");

        for (int i = 0; i < lines.length; ++i) g.drawString (lines[i], this.x + 6, this.y + 30 + i * 10 + offset);

        // Title
        g.setFont (new Font ("Consolas", Font.PLAIN, 18));
        g.drawString (this.title, this.x + 6, this.y + 17 + offset);

        // Clean Up
        g.setColor (originalColor);
        g.setFont (originalFont);
    }

    // Methods

    /**
     * Deselects this item
     */
    public void deselect ()
    {
        this.selected = false;
        this.color    = this.unselectedColor;
        this.updateControlListeners (ControlEvent.getItemDeselect (this.title));
    }

    public String getTitle ()
    {
        return this.title;
    }

    // MouseListener Methods

    public void mousePressed (MouseEvent e)
    {
        if (!this.disabled && this.visible)
        {
            this.selected = !this.selected;

            this.color = (this.selected) ? this.selectedColor : this.unselectedColor;
            
            if (this.selected) this.updateControlListeners (ControlEvent.getItemSelect (this.title));
            else this.updateControlListeners (ControlEvent.getItemDeselect (this.title));
        }
    }
}