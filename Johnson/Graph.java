import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author gabriel
 */
public class Graph
{
    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Pair<Node, Integer>>> edges;

    public Graph()
    {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Parse input graph from file.
     *
     * Input format:
     * N = number of nodes,
     * M = number of edges,
     * M lines with: Node_i Node_j cost_of_edge(i,j)
     * @param filename
     */
    public void initFromFile(String filename)
    {
        Scanner input;

        try {
            input = new Scanner(new File(filename));

            int N = input.nextInt();

            for (int i = 0; i < N; i++) {
                insertNode(new Node(i));
            }

            int M = input.nextInt();

            for (int i = 0; i < M; i++) {
                int node1 = input.nextInt();
                int node2 = input.nextInt();
                int cost = input.nextInt();

                insertEdge(getNode(node1), getNode(node2), cost);
            }

            input.close();
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException("Invalid file path.");
        }
    }

    /**
     *
     * @return The number of nodes in the graph.
     */
    public int getNumberOfNodes()
    {
        return nodes.size();
    }

    /**
     * Get all the nodes in the graph.
     * @return A list containing all the nodes in the graph.
     */
    public ArrayList<Node> getNodes()
    {
        return nodes;
    }

    /**
     * Get all the edges that start from a given node.
     * @param node The node whose edges are wanted.
     * @return A list containing all the edges that start from the node.
     */
    public ArrayList<Pair<Node, Integer>> getEdges(Node node)
    {
        return edges.get(node.getId());
    }

    /**
     * Get a node of the graph.
     * @param id The id of the node.
     * @return The node in the graph with that id.
     */
    public Node getNode(int id)
    {
        return nodes.get(id);
    }

    /**
     * Insert a new node in the graph.
     * @param node The node to be inserted.
     */
    public void insertNode(Node node)
    {
        nodes.add(node);
        edges.add(new ArrayList<>());
    }

    /**
     * Insert a new edge into the graph.
     * @param node1 The node where the edge starts.
     * @param node2 The node where the edge points.
     * @param cost The cost of the edge.
     */
    public void insertEdge(Node node1, Node node2, Integer cost)
    {
        edges.get(node1.getId()).add(new Pair<>(node2, cost));
    }

    /**
     * Update the cost of an edge.
     * @param node1 The node where the edge starts.
     * @param node2 The node where the edge points.
     * @param newCost The new cost of the edge.
     */
    public void updateEdgeCost(Node node1, Node node2, Integer newCost)
    {
        for (Pair<Node, Integer> edge : edges.get(node1.getId()))
            if (edge.getFirst().getId() == node2.getId())
                edge.setSecond(newCost);
    }

    /**
     * Insert a virtual node connected by zero-weight edges to each of the other nodes.
     */
    public void insertVirtualNode()
    {
        int n = getNumberOfNodes();

        nodes.add(new Node(n));
        edges.add(new ArrayList<>());

        for (int i = 0; i < n; i++)
            insertEdge(getNode(n), getNode(i), 0);
    }

    /**
     * Delete the virtual node previously added.
     */
    public void deleteVirtualNode()
    {
        int n = getNumberOfNodes() - 1;

        nodes.remove(n);

        edges.remove(n);
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (Node node : nodes) {
            s.append("Node " + node.getId() + ": {");

            boolean flag = false;
            for (Pair<Node, Integer> edge : getEdges(node)) {
                s.append(edge + ", ");
                flag = true;
            }

            if (flag) {
                int len = s.length();
                s.delete(len - 2, len);
            }

            s.append("}\n");
        }

        return s.toString();
    }
}
