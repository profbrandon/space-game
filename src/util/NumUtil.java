
class NumUtil
{
    public static String formatLong (long l, int padding)
    {
        String temp = Long.toString (l);

        if (padding < 0) return temp;

        while (temp.length () < padding)
        {
            temp = " " + temp;
        }

        return temp;
    }
}