
abstract class SCObject extends Sprite
{
    public static final float G_CONST = 1.0f;
    public static final float DELTA_T = 0.01f;
    public static final float MAX_VEL = 10000f;

    protected float mass;
    protected Vec   pos;
    protected Vec   vel;
    protected Vec   acc;
    protected Vec   dir;

    protected long chunkX;
    protected long chunkY;

    protected Vec getNewAcc (Vec force)
    {
        return Vec.add (this.acc, Vec.scale (force, 1 / mass));
    }

    protected Vec getNewVel ()
    {
        Vec ta = Vec.scale (this.acc, 1 - this.vel.len () / MAX_VEL);

        return Vec.add (Vec.scale (this.vel, 0.99f), Vec.scale (ta, DELTA_T));
    }

    protected Vec getNewPos ()
    {
        Vec ta = Vec.scale (this.acc, 1 - this.vel.len () / MAX_VEL);

        return Vec.add (this.pos, Vec.add (Vec.scale (this.vel, DELTA_T), Vec.scale (ta, 0.5f * DELTA_T * DELTA_T)));
    }

    protected Vec getPos ()
    {
        return this.pos;
    }

    protected Vec getVel ()
    {
        return this.vel;
    }

    protected Vec getAcc ()
    {
        return this.acc;
    }

    protected ChunkCoord getChunkCoord ()
    {
        return new ChunkCoord (this.chunkX, this.chunkY);
    }

    protected long getChunkX ()
    {
        return this.chunkX;
    }

    protected long getChunkY ()
    {
        return this.chunkY;
    }

    protected void setPos (Vec pos)
    {
        this.pos = pos;
        final int HALF = Chunk.CHUNK_SIZE / 2;

        if (this.pos.x <= -HALF)
        {
            this.chunkX -= 1;
            this.pos = Vec.sub (this.pos, new Vec (- Chunk.CHUNK_SIZE, 0));
        }  
        if (this.pos.x > HALF)
        {
            this.chunkX += 1;
            this.pos = Vec.sub (this.pos, new Vec (Chunk.CHUNK_SIZE, 0));
        }

        if (this.pos.y <= -HALF)
        {
            this.chunkY -= 1;
            this.pos = Vec.sub (this.pos, new Vec (0, - Chunk.CHUNK_SIZE));
        }
        if (this.pos.y > HALF)
        {
            this.chunkY += 1;
            this.pos = Vec.sub (this.pos, new Vec (0, Chunk.CHUNK_SIZE));
        }

        this.x = (short) this.pos.x;
        this.y = (short) this.pos.y;

        /*
        System.out.println ("fx: " + this.pos.x + "\tfy: " + this.pos.y);
        System.out.println ("x:  " + this.x + "\ty:  " + this.y);
        System.out.println ("cx: " + this.chunkX + "\tcy: " + this.chunkY);
        */
    }

    protected void setVel (Vec vel)
    {
        this.vel = vel;
    }

    protected void setAcc (Vec acc)
    {
        this.acc = acc;
    }

    protected void setChunkCoord (ChunkCoord cc)
    {
        this.chunkX = cc.x;
        this.chunkY = cc.y;
    }

    protected void setChunkX (long x)
    {
        this.chunkX = x;
    }

    protected void setChunkY (long y)
    {
        this.chunkY = y;
    }

    /**
     * Calculates the force on the first body generated by
     * the second body.
     * @param p1 the first body
     * @param p2 the second body
     */
    public static Vec gravForce (SCObject p1, SCObject p2)
    {
        Vec r = Vec.sub (p1.pos, p2.pos);

        if (r.equals (Vec.ZERO)) return Vec.ZERO;

        return Vec.scale (r.normal (), G_CONST * p1.mass * p2.mass / r.len2 ());
    }
}