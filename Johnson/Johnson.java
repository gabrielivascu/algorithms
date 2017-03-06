import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author gabriel
 */
public class Johnson
{
    private static final int INF = 1000000;
    private static Graph graph;
    private static ArrayList<Integer> dist = new ArrayList<>();
    private static ArrayList<Integer> h = new ArrayList<>();
    private static int[][] shortestPath;

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
     * Compute h(u) values for each node by running Bellman-Ford algorithm
     * having the newly inserted virtual node as source.
     * @return True if negative cycle was detected, False otherwise.
     */
    public static Boolean runBellmanFord()
    {
        int n = graph.getNumberOfNodes();

        int[] count = new int[n];

        // Initializations.
        for (int i = 0; i < n; i++) {
            dist.add(INF);
            count[i] = 0;
        }

        // The virtual node will have id n-1.
        Node s = graph.getNode(n - 1);

        PriorityQueue<Node> Q = new PriorityQueue<>(n, new NodeComparator());

        // Push s to the priority queue.
        dist.set(s.getId(), 0);
        Q.add(s);
        count[s.getId()]++;

        // Run Bellman-Ford.
        while (!Q.isEmpty()) {
            Node u = Q.poll();

            for (Pair<Node, Integer> edge : graph.getEdges(u)) {
                Node v = edge.getFirst();

                // Relax edge.
                if (dist.get(v.getId()) > dist.get(u.getId()) + edge.getSecond()) {
                    dist.set(v.getId(), dist.get(u.getId()) + edge.getSecond());

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

        // No negative cycle.
        h.addAll(dist);
        return false;
    }

    /**
     * Reweight every edge in the graph by the following formula:
     * w(u,v) = w(u,v) + h(u) - h(v).
     */
    public static void reweightEdges()
    {
        for (Node u : graph.getNodes()) {
            for (Pair<Node, Integer> edge : graph.getEdges(u)) {
                Node v = edge.getFirst();
                int newCost = edge.getSecond() + h.get(u.getId()) - h.get(v.getId());

                graph.updateEdgeCost(u, v, newCost);
            }
        }
    }

    /**
     * Compute shortest paths from a source node to all the other nodes by
     * running Dijkstra's algorithm.
     * At the end, values are reweighted back by d(u,v) = d(u,v) + h(v) - h(u) so
     * they will correspond with the original graph.
     * @param source The source node for Dijkstra's algorithm.
     */
    public static void runDijkstra(Node source)
    {
        int n = graph.getNumberOfNodes();
        Boolean[] visited = new Boolean[n];

        // Initialize distances.
        for (int i = 0; i < n; i++) {
            dist.set(i, INF);
            visited[i] = false;
        }

        PriorityQueue<Node> Q = new PriorityQueue<>(n, new NodeComparator());

        // Push source to the priority queue.
        dist.set(source.getId(), 0);
        Q.add(source);

        // Run Dijkstra.
        while (!Q.isEmpty()) {
            Node u = Q.poll();
            visited[u.getId()] = true;

            for (Pair<Node, Integer> edge : graph.getEdges(u)) {
                Node v = edge.getFirst();

                // Relax edge.
                if (!visited[v.getId()] && dist.get(v.getId()) > dist.get(u.getId()) + edge.getSecond()) {
                    dist.set(v.getId(), dist.get(u.getId()) + edge.getSecond());

                    Q.add(v);
                }
            }
        }

        // Reweigth back.
        for (int v = 0; v < n; v++) {
            if (dist.get(v) != INF)
                shortestPath[source.getId()][v] = dist.get(v) + h.get(v) - h.get(source.getId());
            else
                shortestPath[source.getId()][v] = INF;
        }
    }

    public static void main(String[] args)
    {
        graph = new Graph();
        graph.initFromFile("graph.in");

        shortestPath = new int[graph.getNumberOfNodes()][graph.getNumberOfNodes()];

        System.out.println("Graph:");
        System.out.println(graph);

        graph.insertVirtualNode();

        // Run Bellman-Ford algorithm on the virtual node.
        Boolean negativeCycle = runBellmanFord();

        if (negativeCycle) {
            System.out.println("Graph contains negative cycle! Algorithm aborted.");
            return;
        }

        // Reiweight edges: w(u,v) = w(u,v) + h(u) - h(v).
        reweightEdges();

        graph.deleteVirtualNode();

        // Run Dijkstra algorithm for each node with the reweighted edges.
        for (Node u : graph.getNodes())
            runDijkstra(u);

        // Print computed shortest paths.
        System.out.println("Shortest paths matrix:");
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            for (int j = 0; j < graph.getNumberOfNodes(); j++) {
                if (shortestPath[i][j] != INF)
                    System.out.print(shortestPath[i][j] + " ");
                else
                    System.out.print("INF ");
            }

            System.out.println();
        }
    }
}
