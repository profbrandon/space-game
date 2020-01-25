
class Mat
{
    // Static Fields

    public static final Mat ZERO = new Mat ();
    public static final Mat ID   = new Mat (1, 0, 0, 1);

    // Fields

    public final Vec row1;
    public final Vec row2;
    public final Vec col1;
    public final Vec col2;

    // Constructors

    public Mat ()
    {
        this (0, 0, 0, 0);    
    }

    public Mat (float e00, float e10, float e01, float e11)
    {
        this.row1 = new Vec (e00, e10);
        this.row2 = new Vec (e01, e11);
        this.col1 = new Vec (e00, e01);
        this.col2 = new Vec (e10, e11);
    }

    private Mat (Vec row1, Vec row2)
    {
        this.row1 = row1;
        this.row2 = row2;
        this.col1 = new Vec (row1.x, row2.x);
        this.col2 = new Vec (row1.y, row2.y);
    }

    // Methods

    public float det ()
    {
        return row1.x * row2.y - row1.y * row2.x;
    }

    public boolean isID ()
    {
        return row1.x == 1 && row1.y == 0 && row2.x == 0 && row2.y == 1;
    }

    public Mat transpose ()
    {
        if (row2.x == row1.y) return this;
        return new Mat (col1, col2);
    }

    // Static Methods

    public static Mat mult (Mat m1, Mat m2)
    {
        if (m1.isID ()) return m2;
        if (m2.isID ()) return m1;

        return new Mat (
            Vec.dot (m1.row1, m2.col1),
            Vec.dot (m1.row1, m2.col2),
            Vec.dot (m1.row2, m2.col1),
            Vec.dot (m1.row2, m2.col2)
        );
    }

    public static Vec mult (Mat m, Vec v)
    {
        if (m.isID ()) return v;
        return new Vec (
            Vec.dot (m.row1, v),
            Vec.dot (m.row2, v)
        );
    }

    public static Mat rot (float angle)
    {
        angle %= 2 * Math.PI;

        if (angle == 0) return Mat.ID;

        return new Mat (
            (float) Math.cos (angle),
            - (float) Math.sin (angle),
            (float) Math.sin (angle),
            (float) Math.cos (angle)
        );
    }
}