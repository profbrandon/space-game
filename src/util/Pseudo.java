
class Pseudo
{
    public final long seed;

    private final long c1 = 199;

    public Pseudo (long seed)
    {
        this.seed = seed;
    }

    public long getNextLong (long x, long y)
    {
        return (c1 * x + y) % this.seed;
    }
}