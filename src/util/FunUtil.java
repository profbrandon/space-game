

class FunUtil
{

    // Useful functions

    public static int spiral (int x, int y)
    {
        if (x == 0 && y == 0) return 1;

        final int dring    = (int) Math.max (Math.abs (x), Math.abs (y));
        final int enclosed = 1 + (dring * (dring - 1) << 2);

        // Right
        if (Math.abs (y - 0.5) < x)
        {
            return enclosed + (dring << 1) + dring + x;   
        }
        // Left
        if (Math.abs (y + 0.5) < -x)
        {
            return enclosed + 3 * (dring << 1) + dring + y;
        }
        // Top
        if (Math.abs (x + 0.5) < y)
        {
            return enclosed + (dring << 2) + dring - x;
        }
        // Bottom
        if (Math.abs (x - 0.5) < -y)
        {
            return enclosed + dring + x; 
        }

        return -1;
    }
}