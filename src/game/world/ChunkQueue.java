
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.PrintWriter;

class ChunkQueue implements ActionListener
{
    // Fields

    private String root;

    private Deque<Chunk> deque;
    private Map<ChunkCoord, Chunk> map;

    private Timer timer;

    // Constructors

    public ChunkQueue (String root)
    {
        this.root  = root;
        this.deque = new LinkedList<> ();
        this.map   = new HashMap<> ();
        this.timer = new Timer (60, this);
        this.timer.setInitialDelay (200);
    }


    // Methods

    public Chunk get (ChunkCoord cc)
    {
        Chunk chunk = null;

        // If chunk has not been written yet, retrieve chunk
        if (this.map.containsKey (cc))
        {
            chunk = this.map.get (cc);

            this.map.remove (cc);
            this.deque.remove (this.map.get (cc));

            return chunk;
        }

        // Get from a file
        try
        {
            File cfile = new File (root + "/chunk_" + cc.x + "_" + cc.y);

            if (cfile.exists ())
            {
                Scanner scanner = new Scanner (cfile);

                List<String> lines = new ArrayList<> ();

                while (scanner.hasNext ()) lines.add (scanner.nextLine ());

                chunk = Chunk.convert (lines);

                scanner.close ();
            }
        }
        catch (Exception e)
        {
            System.out.println ("Failed to Load Chunk File: " + e);
        }

        return chunk;
    }

    public void put (Chunk chunk) 
    {
        this.deque.addFirst (chunk);
        this.map.put (chunk.getChunkCoord (), chunk);

        if (!this.timer.isRunning ()) this.timer.start ();
    }

    public void actionPerformed (ActionEvent e)
    {
        if (this.deque.size () == 0)
        {
            this.timer.stop ();
            //System.out.println ("Finished writing chunks");
        }
        else
        {
            this.writeChunk (this.deque.pollLast ());
        }
    }

    public void purge ()
    {
        while (this.deque.size () != 0)
        {
            this.writeChunk (this.deque.pollLast ());
        }

        if (this.timer.isRunning ()) this.timer.stop ();
    }

    public int size ()
    {
        return this.deque.size ();
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
            File file = new File (root + "/chunk_" + chunk.x + "_" + chunk.y);
            PrintWriter pw = new PrintWriter (file);

            pw.print (chunk.toString ());
            pw.close ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Writing Chunk at (" + chunk.x + ", " + chunk.y + "): " + e);
        }
    }
}