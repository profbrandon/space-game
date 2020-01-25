
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import java.io.File;
import java.io.PrintWriter;

/**
 * Class to represent a world. The world handles loading chunks,
 * which are small parts of the world.
 */
class World
{
    // Static Fields

    private static final int RENDER_DIST = 30;
    private static final int MAX_CHUNKS  = (1 + RENDER_DIST) * (1 + RENDER_DIST);

    // Fields

    private Pseudo random;
    private String name;
    private Map<ChunkCoord, Chunk> chunkMap;
    private List<Chunk> loaded;
    private Ship player;
    private ChunkCoord center;

    // Constructors

    public World (String name)
    {
        this (name, RandUtil.randLong ());
    }

    public World (String name, long seed)
    {
        this.name = name;
        
        this.chunkMap = new HashMap<> ();
        this.loaded = new ArrayList<> ();

        this.player = new Ship ("textures/ship.png");
        this.center = null;

        this.random = new Pseudo (seed);
        
        try
        {
            File dir = new File ("../data/worlds/" + name);

            dir.mkdir ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Creating World Directory: " + e);
        }

        try
        {
            File worlds = new File ("../data/worlds/worlds.txt");

            PrintWriter pw = new PrintWriter (worlds);

            pw.println (this.name);
            pw.close ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Appending to World List File");
        }

        this.writeStats ();
    }

    private World (String name, long seed, String shipPath, long chunkX, long chunkY, float x, float y)
    {
        this.name = name;
        
        this.player = new Ship (shipPath);
        this.player.setChunkX (chunkX);
        this.player.setChunkY (chunkY);
        this.player.setPos (new Vec (x, y));

        this.center = null;

        this.random = new Pseudo (seed);

        this.chunkMap = new HashMap<> ();
        this.loaded = new ArrayList<> ();
    }

    // Methods

    /**
     * Gets the chunk specified by the given chunk coordinate.
     * If no such chunk has been generated, then it is generated
     * and returned to the user. In the event that too many
     * chunks are loaded, the furthest from the player is written
     * to a chunk file and released.
     * 
     * @param cc the chunk coordinate
     * @return a chunk at the specified coordinates
     */
    public Chunk getChunk (ChunkCoord cc)
    {
        if (chunkMap.containsKey (cc))
        {
            return chunkMap.get (cc);
        }
        else
        {
            Chunk chunk = null;

            try
            {
                File cfile = new File ("../data/worlds/" + this.name + "/chunk_" + cc.x + "_" + cc.y);

                if (cfile.exists ())
                {
                    Scanner scanner = new Scanner (cfile);

                    List<String> lines = new ArrayList<> ();

                    while (scanner.hasNext ()) lines.add (scanner.nextLine ());

                    chunk = Chunk.convert (lines);
                }
            }
            catch (Exception e)
            {
                System.out.println ("Failed to Load Chunk File: " + e);
            }

            if (chunk == null)
            {
                chunk = new Chunk (cc);
                chunk.setObjects (this.generate (cc));

                if (loaded.size () == MAX_CHUNKS)
                {
                    Chunk toRemove = this.maxDist (loaded);
                    this.loaded.remove (toRemove);
                    this.chunkMap.remove (toRemove);

                    //System.out.println ("Writing Chunk at (" + toRemove.x + ", " + toRemove.y + ")");
                    this.writeChunk (toRemove);
                }
            }

            this.chunkMap.put (cc, chunk);
            this.loaded.add (chunk);

            return chunk;
        }
    }

    /**
     * Returns the chunks surrounding and including the
     * specified chunk coordinate. Specifically, it ret-
     * rieves a 3x3 square of chunks centered on the
     * given coordinate.
     * 
     * @param cc the center chunk coordinate
     * @return a 3x3 square of chunks centered at cc
     */
    public Chunk[][] getLocale (ChunkCoord cc)
    {
        Chunk[][] locale = new Chunk[3][3];

        this.render ();

        for (short x = -1; x <= 1; ++x)
        {
            for (short y = -1; y <= 1; ++y)
            {
                ChunkCoord coord = new ChunkCoord (cc.x + x, cc.y + y);

                locale[1 + x][1 + y] = this.getChunk (coord);
            }
        }

        return locale;
    }

    /**
     * @return the player
     */
    public Ship getPlayer ()
    {
        return this.player;
    }

    /**
     * Generates the objects at the specified chunk coordinate.
     *
     * @param x the x coordinate for the chunk
     * @param y the y coordiante for the chunk
     */
    public List<SCObject> generate (ChunkCoord cc)
    {
        return new ArrayList<> ();
    }

    /**
     * Writes the specified chunk to a file in the world
     * directory.
     *
     * @param chunk the chunk to write
     */
    private void writeChunk (Chunk chunk)
    {
        try 
        {
            File file = new File ("../data/worlds/" + name + "/chunk_" + chunk.x + "_" + chunk.y);
            PrintWriter pw = new PrintWriter (file);

            pw.print (chunk.toString ());
            pw.close ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Writing Chunk at (" + chunk.x + ", " + chunk.y + "): " + e);
        }
    }

    private void writeStats ()
    {
        try
        {
            File stats = new File ("../data/worlds/" + name + "/stats");

            PrintWriter pw = new PrintWriter (stats);

            pw.println (this.random.seed);
            pw.println (this.player.getPath ());
            pw.println (this.player.getChunkX ());
            pw.println (this.player.getChunkY ());
            pw.println (this.player.getPos ().x);
            pw.println (this.player.getPos ().y);
            pw.close ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Creating World File: " + e);
        }
    }

    /**
     * Calculates the chunk with the maximum distance to
     * the player.
     *
     * @param cs the chunks to sift through
     *
     * @return the furthest chunk
     */
    private Chunk maxDist (List<Chunk> cs)
    {
        return Collections.max (cs, (Chunk c1, Chunk c2) -> {
            final long shipX = this.player.getChunkX ();
            final long shipY = this.player.getChunkY ();

            final short dx1 = (short) (c1.x - shipX);
            final short dy1 = (short) (c1.y - shipY);
            final short dx2 = (short) (c2.x - shipX);
            final short dy2 = (short) (c2.y - shipY);

            final int dist1 = dx1 * dx1 + dy1 * dy1;
            final int dist2 = dx2 * dx2 + dy2 * dy2;

            if (dist1 > dist2) return 1;
            if (dist1 < dist2) return -1;
            
            return 0;
        });
    }

    private void render ()
    {
        if (this.player.getChunkCoord ().equals (this.center)) return;

        this.center = this.player.getChunkCoord ();

        for (long x = - RENDER_DIST; x <= RENDER_DIST; ++x)
        {
            for (long y = - RENDER_DIST; y <= RENDER_DIST; ++y)
            {
                getChunk (new ChunkCoord (this.center.x + x, this.center.y + y));
            }
        }
    }

    // Static Methods

    /**
     * Loads a world from the <name>.world text file. Returns null if
     * the world could not be loaded.
     *
     * @param name the name of the world to load
     *
     * @return the loaded world
     */
    public static World loadWorld (String name)
    {
        // TODO: Load world stats from file

        String stats = (String) ResourceManager.loadText ("../data/worlds/" + name + "/stats").getValue ();

        String[] lines = stats.split ("\n");

        World world = new World (
              name
            , Long.parseLong (lines[0])   // Seed
            , lines[1]                    // Ship Path
            , Long.parseLong (lines[2])   // Chunk X
            , Long.parseLong (lines[3])   // Chunk Y
            , Float.parseFloat (lines[4]) // Pos X
            , Float.parseFloat (lines[5]) // Pos Y
        );

        world.render ();

        return world;
    }

    /**
     * Writes a world to a directory.
     *
     * @param world the world to be converted
     */
    public static void writeWorld (World world)
    {
        for (Chunk c : world.loaded)
        {
            world.writeChunk (c);
        }

        world.writeStats ();
    }
}