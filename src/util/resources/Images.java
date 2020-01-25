
import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;

class Images
{
    public static List<BufferedImage> buildTextureSheet (BufferedImage img)
    {
        List<BufferedImage> textureSheet = new ArrayList<> ();

        if (img == null)
        {
            textureSheet.add (null);
        }
        else
        {
            int len = img.getHeight ();
            int count = (int) (img.getWidth () / len);

            for (int i = 0; i < count; ++i)
                textureSheet.add (img.getSubimage (i * len, 0, len, len));
        }

        return textureSheet;
    }
}