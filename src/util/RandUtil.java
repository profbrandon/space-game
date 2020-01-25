
import java.util.List;

class RandUtil
{
    /**
     * Obtains a random integer on the range [bottom,top].
     * @param bottom the bottom of the range
     * @param top the top of the range
     */
    public static int randInt (int bottom, int top)
    {
        final int size = Math.abs (top - bottom);

        return (int) (Math.random () * (size + 1)) + bottom;
    }

    public static long randLong (long bottom, long top)
    {
        final long size = Math.abs (top - bottom);

        return (long) (Math.random () * (size + 1)) + bottom;
    }

    /**
     * Obtains a random integer on the range [0,top].
     * @param top the top of the range
     */
    public static int randInt (int top)
    {
        return RandUtil.randInt (0, top);
    }

    public static int randInt ()
    {
        return RandUtil.randInt (Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static long randLong (long top)
    {
        return RandUtil.randLong (0, top);
    }

    public static long randLong ()
    {
        return RandUtil.randLong (Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Retrieves a random element from a list
     * @param list the list
     * @return a random element of list
     */
    public static <T> T getRand (List<T> list)
    {
        return list.get (RandUtil.randInt (list.size () - 1));
    }
}