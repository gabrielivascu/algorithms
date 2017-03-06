import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author gabriel
 */
public class BellmanFord
{
    private static final int INF = 1000000;
    private static Graph graph;
    private static ArrayList<Integer> dist = new ArrayList<>();
    private static final int SOURCE = 4;

    private static class NodeComparator implements Comparator<Node>
    {
        /**
         * Compares nodes using the current estimation of the distance from the
         * source node.
         * @param n1 The first node to be tested.
         * @param n2 The second node to be tested.
         * @return 1 if the dist(n1) > dist(n2), -1 otherwise
         */
        @Override
        public int compare(Node n1, Node n2) {
            int dist1 = dist.get(n1.getId());
            int dist2 = dist.get(n2.getId());

            return dist1 > dist2 ? 1 : -1;
        }
    }

    /**
     * Compute shortest paths from the SOURCE node to all the other nodes in the
     * graph using Bellman-Ford algorithm.
     * @return True if negative cycle detected, False otherwise.
     */
    private static boolean runBellmanFord()
    {
        int n = graph.getNumberOfNodes();

        int[] count = new int[n];

        // Initializations.
        for (int i = 0; i < n; i++) {
            dist.add(INF);
            count[i] = 0;
        }

        PriorityQueue<Node> Q = new PriorityQueue<>(n, new NodeComparator());

        // Push SOURCE node to the priority queue.
        Node s = graph.getNode(SOURCE);
        dist.set(s.getId(), 0);
        Q.add(s);
        count[s.getId()]++;

        // Run Bellman-Ford algorithm.
        while (!Q.isEmpty()) {
            Node u = Q.poll();

            for (Pair<Node, Integer> edge : graph.getEdges(u)) {
                Node v = edge.getFirst();

                // Relax edge.
                if (dist.get(v.getId()) > dist.get(u.getId()) + edge.getSecond()) {
                    dist.set(v.getId(), dist.get(u.getId()) + edge.getSecond());
                    v.setParent(u);

                    if (!Q.contains(v)) {
                        Q.add(v);
                        count[v.getId()]++;

                        // Check negative cycle.
                        if (count[v.getId()] > n)
                            return true;
                    }
                }
            }
        }

        // No negative cycle detected.
        return false;
    }

    /**
     * Print the node to node shortest path from SOURCE to node u.
     * @param u The node whose path to print.
     */
    private static void printPath(Node u)
    {
        Node p = u.getParent();
        Stack<Node> S = new Stack<>();

        while (p != null) {
            S.push(p);
            p = p.getParent();
        }

        while (!S.isEmpty()) {
            p = S.pop();
            System.out.print(p + " -> ");
        }

        System.out.println(u);
    }

    public static void main(String[] args)
    {
        // Read input.
        graph = new Graph();
        graph.initFromFile("graph.in");

        System.out.println("Graph:");
        System.out.println(graph);

        // Run Bellman-Ford algorithm.
        Boolean negativeCycle = runBellmanFord();

        if (!negativeCycle) {
            // Print computed shortest paths.
            System.out.println("Shortest paths from source node " + SOURCE + ":");
            for (int i = 0; i < graph.getNumberOfNodes(); i++) {
                System.out.print("To node " + i + " - distance: " + dist.get(i) + ", path: ");
                printPath(graph.getNode(i));
            }
        }
        else {
            System.out.println("Error. Graph contains negative cycle.");
        }
    }
}
