
class ChunkCoord
{
    public final long x;
    public final long y;

    public ChunkCoord (long x, long y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean equals (Object obj)
    {
        if (obj instanceof ChunkCoord)
        {
            ChunkCoord c = (ChunkCoord) obj;

            return this.x == c.x && this.y == c.y;
        }
        else
        {
            return this == obj;
        }
    }

    public int hashCode ()
    {
        return (int) (x * y + x + y);
    }
}