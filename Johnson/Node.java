/**
 *
 * @author gabriel
 */
public class Node
{
    private int id;
    private Node parent;

    public Node(int id)
    {
        this.id = id;
        parent = null;
    }

    public int getId()
    {
        return id;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    @Override
    public String toString()
    {
        return "" + id;
    }
}
