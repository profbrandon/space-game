
import java.util.List;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class ScrollMenu extends Control implements ControlListener, MouseMotionListener
{
    // Static Fields

    private static final int SCROLL_BAR_WIDTH  = 10;
    private static final int ITEM_HEIGHT       = 80;
    private static final int ITEM_SPACING      = 5;

    // Fields

    private int scrollBarHeight;
    private int totalItemHeight;
    private float scrollPercent = 0.0f;

    private Color color;

    private boolean scrollDisabled = false;
    private boolean enableScrollDragging = false;

    private List<ScrollMenuItem> items;
    private ScrollMenuItem selected = null;

    // Constructors

    /**
     * Creates a new scroll menu.
     *
     * @param x the center x coordinate
     * @param y the center y coordinate
     * @param width the width of the menu
     * @param height the height of the menu
     * @param color the default (unselected) color
     * @param action the action to execute upon selection
     */
    public ScrollMenu (int x, int y, int width, int height, Color color, Action action)
    {
        this.x = x;
        this.y = y;
        this.width = Math.max (width, SCROLL_BAR_WIDTH + 2 * ITEM_SPACING);
        this.height = height;

        this.leftX   = this.x - (this.width / 2);
        this.rightX  = this.x + (this.width / 2);
        this.topY    = this.y - (this.height / 2);
        this.bottomY = this.y + (this.height / 2);

        this.color = color;

        this.items = new ArrayList<> ();
        
        this.totalItemHeight = ITEM_SPACING;
        this.scrollBarHeight = (this.totalItemHeight <= this.height) ? 
                               this.height : (int) (this.height * this.height / this.totalItemHeight);
        this.scrollDisabled  = this.totalItemHeight <= this.height;

        this.action   = action;
        this.disabled = false;
        this.visible  = true;
    }

    // Methods

    @Override
    public void paint (Graphics g)
    {
        if (!this.visible) return;

        float dim = 1f;

        if (this.disabled) dim = 0.6f;

        Color originalColor = g.getColor ();
        Font  originalFont  = g.getFont ();

        // Scroll Menu Background
        g.setColor (ColorUtil.scale (this.color, 0.10 * dim));
        g.fillRect (this.leftX + 1, this.topY + 1, this.width - 1 - SCROLL_BAR_WIDTH, this.height - 1);
        
        // Scroll Bar Background
        g.setColor (ColorUtil.scale (this.color, 0.20 * dim));
        g.fillRect (this.rightX - SCROLL_BAR_WIDTH, this.topY + 1, SCROLL_BAR_WIDTH, this.height - 1);

        int scrollY = this.topY + (int) (this.scrollPercent * (this.height - this.scrollBarHeight));

        // Scroll Bar
        g.setColor ((this.enableScrollDragging) ? ColorUtil.scale (this.color, 0.50) : ColorUtil.scale (this.color, 0.40 * dim));
        g.fillRect (this.rightX - SCROLL_BAR_WIDTH, scrollY, SCROLL_BAR_WIDTH, this.scrollBarHeight);

        // Borders
        g.setColor (ColorUtil.scale (this.color, 0.75 * dim));
        g.drawRect (this.leftX, this.topY, this.width, this.height);

        // Draw Items
        g.setClip (this.leftX + 1, this.topY + 1, this.width - 1 - SCROLL_BAR_WIDTH, this.height - 1);
        
        final int maxOffset = (this.items.size () == 0) ? 0 : this.totalItemHeight - this.height;

        for (ScrollMenuItem item : items) item.paint (g, - (int) (this.scrollPercent * maxOffset));

        // Clean Up
        g.setColor (originalColor);
        g.setFont (originalFont);
    }

    /**
     * @return whether the position is over the scroll bar (including the scroller)
     */
    public boolean overScrollBar (int x, int y)
    {
        return x > this.rightX - SCROLL_BAR_WIDTH && x < this.rightX
            && y > this.topY && y < this.bottomY;
    }

    /**
     * @return whether the position is over the scroller
     */
    public boolean overScroll (int x, int y)
    {
        final int scrollY = this.topY + (int) (this.scrollPercent * (this.height - this.scrollBarHeight));

        return x > this.rightX - SCROLL_BAR_WIDTH && x < this.rightX
            && y > scrollY && y < scrollY + this.scrollBarHeight;
    }

    /**
     * Adds a scroll menu item to the menu with the title and the text.
     * 
     * @param title the title for the scroll menu
     * @param text the text for the scroll menu
     */
    public void addItem (String title, String text)
    {
        ScrollMenuItem item = new ScrollMenuItem (
              title
            , text
            , this.leftX + ITEM_SPACING
            , this.topY + this.totalItemHeight
            , this.width - 2 * ITEM_SPACING - SCROLL_BAR_WIDTH
            , ITEM_HEIGHT
            , this.color
        );

        this.items.add (item);

        item.addControlListener (this);

        this.totalItemHeight = (items.size () == 0) ? 0
                             : items.size () * ITEM_HEIGHT + (items.size () + 1) * ITEM_SPACING;

        this.scrollBarHeight = (this.totalItemHeight <= this.height) ? 
                               this.height : (int) (this.height * this.height / this.totalItemHeight);
        this.scrollDisabled  = this.totalItemHeight <= this.height;
    }

    /**
     * Removes the ScrollMenuItem from this ScrollMenu
     * (If it exists).
     *
     * @param title the title of the item to remove
     */
    public void removeItem (String title)
    {
        ScrollMenuItem toRemove = null;

        for (ScrollMenuItem item : this.items)
        {
            if (item.getTitle ().equals (title)) toRemove = item;
        }

        if (toRemove == null) return;

        this.items.remove (toRemove);
        toRemove.removeControlListener (this);

        this.totalItemHeight = (items.size () == 0) ? 0
                             : items.size () * ITEM_HEIGHT + (items.size () + 1) * ITEM_SPACING;

        this.scrollBarHeight = (this.totalItemHeight <= this.height) ? 
                               this.height : (int) (this.height * this.height / this.totalItemHeight);
        this.scrollDisabled  = this.totalItemHeight <= this.height;
    }

    /**
     * @return null if no item is selected, otherwise the title of the item
     */
    public String getSelected ()
    {
        if (this.selected == null) return null;

        return this.selected.getTitle ();
    }

    @Override
    public void setVisible (boolean visible)
    {
        this.visible = visible;

        for (ScrollMenuItem item : items) item.setVisible (visible);
    }

    @Override
    public void setDisabled (boolean disable)
    {
        this.disabled = disable;

        for (ScrollMenuItem item : items) item.setDisabled (disable);
    }

    // ControlListener Methods

    public void controlOccurred (ControlEvent e)
    {
        switch (e.type)
        {
            case SCROLL_ITEM_SELECT:
                for (ScrollMenuItem item : items)
                {
                    if (!item.getTitle ().equals (e.caller)) item.deselect ();
                    else this.selected = item;
                }
                this.action.execute ();
                break;

            case SCROLL_ITEM_DESELECT:
                //this.selected = null;
                break;

            default:
                break;
        }

        this.updateControlListeners (e);
    }

    // MouseListener Methods

    public void mousePressed (MouseEvent e)
    {
        final int x = e.getX ();
        final int y = e.getY ();

        if (!this.disabled && !this.scrollDisabled && this.overScroll (x, y))
        {
            this.enableScrollDragging = true;
        }
        else if (!this.disabled && !this.scrollDisabled && this.overScrollBar (x, y))
        {
            final int total = this.height - this.scrollBarHeight;
            final int piece = y - this.topY - (this.scrollBarHeight / 2);

            final float temp = (total == 0) ? 1.0f : (float) (piece / (float) total);

            this.scrollPercent = Math.min (1.0f, Math.max (0.0f, temp));
        }

        if (!this.disabled && this.over (x, y))
        {
            final int maxOffset = (this.items.size () == 0) ? 0 : this.totalItemHeight - this.height;

            for (ScrollMenuItem item : this.items)
            {
                if (item.over (x, y + (int) (this.scrollPercent * maxOffset))) item.mousePressed (e);
            }
        }

        updateControlListeners (ControlEvent.NULL);
    }

    public void mouseReleased (MouseEvent e)
    {
        this.enableScrollDragging = false;

        if (!this.disabled)
        {
            for (ScrollMenuItem item : this.items)
            {
                item.mouseReleased (e);
            }
        }

        updateControlListeners (ControlEvent.NULL);
    }

    // MouseMotionListener Methods

    public void mouseMoved (MouseEvent e) { }

    public void mouseDragged (MouseEvent e)
    {
        final int x = e.getX ();
        final int y = e.getY ();

        if (!this.disabled && this.enableScrollDragging)
        {
            final int total = this.height - this.scrollBarHeight;
            final int piece = y - this.topY - (this.scrollBarHeight / 2);

            final float temp = (total == 0) ? 1.0f : (float) (piece / (float) total);

            this.scrollPercent = Math.min (1.0f, Math.max (0.0f, temp));

            updateControlListeners (ControlEvent.NULL);
        }
    }
}