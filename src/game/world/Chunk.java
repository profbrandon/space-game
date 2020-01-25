
import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;

class Chunk
{
    // Static Fields

    public static final int CHUNK_SIZE = 2400;

    public static final List<BufferedImage> TEXTURES = Images.buildTextureSheet (
        (BufferedImage) ResourceManager.loadImage ("textures/chunks.png").getValue ()
    );

    // Fields

    // Constant Fields
    public final long x;
    public final long y;
    public final short texture;

    // Variable Fields
    public List<SCObject> objects;

    // Constructors

    public Chunk (long x, long y)
    {
        this (x, y, (short) RandUtil.randInt (Chunk.TEXTURES.size () - 1));
    }

    public Chunk (ChunkCoord cc)
    {
        this (cc.x, cc.y);
    }

    private Chunk (long x, long y, short texture)
    {
        this.x = x;
        this.y = y;

        this.texture = texture;
        this.objects = new ArrayList<> ();
    }

    // Methods

    /**
     * @return the coordinate for this chunk
     */
    public ChunkCoord getChunkCoord ()
    {
        return new ChunkCoord (this.x, this.y);
    }

    /**
     * @return the objects owned by this chunk
     */
    public List<SCObject> getObjects ()
    {
        return this.objects;
    }

    /**
     * @param objs the objects to set
     */
    public void setObjects (List<SCObject> objs)
    {
        this.objects = objs;
    }

    /**
     * Equality in the natural fashion--componentwise.
     * @param obj the object to test against
     */
    @Override
    public boolean equals (Object obj)
    {
        if (obj instanceof Chunk)
        {
            Chunk ck = (Chunk) obj;

            return this.x == ck.x && this.y == ck.y;
        }

        return this == obj;
    }

    public String toString ()
    {
        return Long.toString (this.x) + "\n" + 
            Long.toString (this.y) + "\n" + 
            Integer.toString (this.texture);
    }

    // Static Methods

    public static Chunk convert (List<String> lines)
    {
        long x = Long.parseLong (lines.get (0));
        long y = Long.parseLong (lines.get (1));
        short texture = Short.parseShort (lines.get (2));

        return new Chunk (x, y, texture);
    }
}