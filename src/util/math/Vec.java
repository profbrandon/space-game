

class Vec
{
    // Static Fields

    public static final Vec ZERO = new Vec ();
    public static final Vec UNIT_X = new Vec (1, 0);
    public static final Vec UNIT_Y = new Vec (0, 1);

    // Fields

    public final float x;
    public final float y;

    // Constructors

    public Vec ()
    {
        this (0, 0);
    }

    public Vec (float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    // Methods

    public boolean isZero ()
    {
        return x == 0 && y == 0;
    }

    public float len ()
    {
        return (float) Math.sqrt ((double) Vec.dot (this, this)); 
    }

    public float len2 ()
    {
        return Vec.dot (this, this);
    }

    public Vec normal ()
    {
        return scale (this, (float) (1.0 / this.len ()));
    }

    // Static Methods

    public static Vec scale (Vec v, float s)
    {
        if (s == 1.0) return v;
        if (s == 0.0) return Vec.ZERO;
        if (s == -1.0) return new Vec (-v.x, -v.y);
        else return new Vec (v.x * s, v.y * s);
    }

    public static Vec add (Vec v1, Vec v2)
    {
        if (v1.isZero ()) return v2;
        if (v2.isZero ()) return v1;

        return new Vec (v1.x + v2.x, v1.y + v2.y);
    }

    public static Vec sub (Vec v1, Vec v2)
    {
        return Vec.add (v1, Vec.scale (v2, -1));
    }

    public static float dot (Vec v1, Vec v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }
}