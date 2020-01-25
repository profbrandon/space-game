
class Resource
{
    private String path;

    private Object value;

    public Resource (String path)
    {
        this (path, null);
    }

    public Resource (String path, Object value)
    {
        this.path = path;
        this.value = value;
    }

    public boolean equals (Resource r)
    {
        return this.path == r.path;
    }

    public Object getValue ()
    {
        return this.value;
    }

    public String getPath ()
    {
        return this.path;
    }
}