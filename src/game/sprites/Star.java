
import java.awt.image.BufferedImage;

class Star extends SCObject
{
    public Star (String path, long chunkX, long chunkY)
    {
        this.scale = 4.0f;
        this.x        = 0;
        this.y        = 0;
        this.rotation = 0;
        this.path     = path;
        this.texture  = 0;

        this.mass = 1000f;
        this.pos  = Vec.ZERO;
        this.vel  = Vec.ZERO;
        this.acc  = Vec.ZERO;
        this.dir  = Vec.UNIT_Y;

        this.chunkX = chunkX;
        this.chunkY = chunkY;

        BufferedImage texture = (BufferedImage) ResourceManager.loadResource (path).getValue ();

        if (texture != null) this.textureSheet = Images.buildTextureSheet (texture);
    }

    public Vec getNewVel () { return Vec.ZERO; }
    public Vec getNewPos () { return Vec.ZERO; }
    public Vec getNewAcc () { return Vec.ZERO; }

    public void setPos (Vec pos)
    {
        this.pos = pos;
        final int HALF = Chunk.CHUNK_SIZE / 2;

        if (this.pos.x <= -HALF)
        {
            this.pos = Vec.sub (this.pos, new Vec (Chunk.CHUNK_SIZE, 0));
        }  
        if (this.pos.x > HALF)
        {
            this.pos = Vec.sub (this.pos, new Vec (- Chunk.CHUNK_SIZE, 0));
        }

        if (this.pos.y <= -HALF)
        {
            this.pos = Vec.sub (this.pos, new Vec (0, Chunk.CHUNK_SIZE));
        }
        if (this.pos.y > HALF)
        {
            this.pos = Vec.sub (this.pos, new Vec (0, - Chunk.CHUNK_SIZE));
        }

        this.x = (short) this.pos.x;
        this.y = (short) this.pos.y;

        /*
        System.out.println ("fx: " + this.pos.x + "\tfy: " + this.pos.y);
        System.out.println ("x:  " + this.x + "\ty:  " + this.y);
        System.out.println ("cx: " + this.chunkX + "\tcy: " + this.chunkY);
        */
    }
}