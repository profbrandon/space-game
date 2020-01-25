
class ControlEvent
{
    // Static Fields

    public static enum Type 
    {
          NULL
        , BUTTON_PRESS
        , BUTTON_RELEASE
        , SCROLL_DOWN
        , SCROLL_UP
        , SCROLL_ITEM_SELECT
        , SCROLL_ITEM_DESELECT
    }

    public static final ControlEvent NULL = new ControlEvent (ControlEvent.Type.NULL);

    // Fields

    public final String caller;
    public final Type type;

    // Constructors

    public ControlEvent (Type type)
    {
        this (type, null);
    }

    public ControlEvent (Type type, String caller)
    {
        this.type   = type;
        this.caller = caller;
    }

    // Static Methods

    public static ControlEvent getItemSelect (String title)
    {
        return new ControlEvent (ControlEvent.Type.SCROLL_ITEM_SELECT, title);
    }

    public static ControlEvent getItemDeselect (String title)
    {
        return new ControlEvent (ControlEvent.Type.SCROLL_ITEM_DESELECT, title);
    }
}