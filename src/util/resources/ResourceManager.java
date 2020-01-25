
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;

class ResourceManager
{
    private static List<Resource> loaded = new ArrayList<> ();
    private static String root = "./";

    public static Resource loadResource (String path)
    {
        Resource resource = new Resource (root + path);

        // Check for previously loaded resources
        for (Resource r : loaded)
        {
            if (resource.equals (r)) return r;
        }

        // Load new resource
        if (path.endsWith (".png") || path.endsWith (".jpg") || path.endsWith (".img"))
        {
            resource = loadImage (path);
        }
        else if (path.endsWith (".txt"))
        {
            resource = loadText (path);
        }
        else
        {
            System.out.println ("Error Loading Resource: Unknown file extension");
        }

        loaded.add (resource);

        return resource;
    }

    public static Resource loadImage (String path)
    {
        String cpath = root + path;

        BufferedImage img = null;
        Resource resource = new Resource (cpath);

        try
        {
            img = ImageIO.read (new File (cpath));

            resource = new Resource (cpath, img);
        }
        catch (Exception e)
        {
            System.out.println ("Error Loading Image '" + path + "': " + e.toString ());
        }

        return resource;
    }

    public static Resource loadText (String path)
    {
        String cpath = root + path;
        Resource resource = new Resource (cpath);

        try 
        {
            Scanner scanner = new Scanner (new File (cpath));

            String text = "";

            while (scanner.hasNext ())
            {
                text += scanner.nextLine ();
                text += "\n";
            }

            resource = new Resource (cpath, text);

            scanner.close ();
        }
        catch (Exception e)
        {
            System.out.println ("Error Loading Text '" + path + "': " + e.toString ());
        } 

        return resource;
    }

    public static void setRoot (String root)
    {
        ResourceManager.root = root;
    }
}