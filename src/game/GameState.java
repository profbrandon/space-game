
import java.util.List;

class GameState
{
    // Statistics

    public final int tick;              // The current game tick
    public final short playerX;         // The player's x position in the current chunk
    public final short playerY;         // The player's y position in the current chunk
    public final long chunkX;           // The player's chunk x position
    public final long chunkY;           // The player's chunk y position
    public final float speed;           // The player's speed

    // Rendering information

    public final List<Sprite> sprites;  // Sprites to render
    public final Chunk[][] chunks;      // 3x3 array of chunks to draw

    // Constructors

    public GameState (int tick, List<Sprite> sprites, Chunk[][] chunks, short px, short py, long cx, long cy, float speed)
    {
        this.tick    = tick;
        this.sprites = sprites;
        this.chunks  = chunks;
        this.playerX = px;
        this.playerY = py;
        this.chunkX  = cx;
        this.chunkY  = cy;
        this.speed   = speed;
    }
}