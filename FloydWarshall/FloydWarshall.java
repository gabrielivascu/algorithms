/**
 *
 * @author gabriel
 */
public class FloydWarshall
{
    private static Graph graph;
    private static int[][] d;
    private static int[][] p;

    /**
     * Compute all-pair shortest paths using Floyd-Warshall algorithm.
     */
    private static void runFloydWarshall()
    {
        int n = graph.getNumberOfNodes();

        d = new int[n][n];
        p = new int[n][n];

        // Initializations.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Node u = graph.getNode(i);
                Node v = graph.getNode(j);

                d[i][j] = graph.getCost(u, v);

                if (d[i][j] == graph.INF)
                    p[i][j] = -1;
                else
                    p[i][j] = i;
            }
            d[i][i] = 0;
            p[i][i] = -1;
        }

        // Run Floyd-Warshall algorithm.
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (d[i][j] > d[i][k] + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                        p[i][j] = p[k][j];
                    }
    }

    /**
     * Print the shortest paths computed by Floyd-Warshall algorithm.
     */
    private static void printResults()
    {
        System.out.println("The minimum distance matrix:");
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            for (int j = 0; j < graph.getNumberOfNodes(); j++)
                System.out.print(d[i][j] + " ");
            System.out.println();
        }

        System.out.println("\nThe parent node matrix:");
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            for (int j = 0; j < graph.getNumberOfNodes(); j++)
                System.out.print(p[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        // Read input.
        graph = new Graph();
        graph.initFromFile("graph.in");

        System.out.println("Graph:");
        System.out.println(graph);

        // Run Floyd-Warshall algorithm.
        runFloydWarshall();

        // Print the results.
        printResults();
    }
}
