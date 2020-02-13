
class Pseudo
{
    public final long seed;

    private final long c1 = 49979687;
    private final long c2 = 920419823;
    private final long c3 = 982451653;

    public Pseudo (long seed)
    {
        this.seed = seed;
    }

    public long getLong (long x, long y)
    {
        int c = FunUtil.spiral ((int) x, (int) y);

        return (c3 * this.seed + c2 * (c - this.seed) + c1) % (2 << 24);
    }

    /*
    public static void main (String[] args)
    {
        
        for (int j = -30; j <= 30; ++j)
        {
            for (int i = -7; i <= 7; ++i)
            {
                Pseudo pseudo = new Pseudo (129873248);

                if (i != 7)
                    System.out.print ("(" + pseudo.getLong (i, j) + "," + j + "), ");
                else
                    System.out.println ("(" + pseudo.getLong (i, j) + "," + j + ")");
            }

            System.out.println ("");
        }
    }
    */
}