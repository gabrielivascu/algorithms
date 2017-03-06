#### Johnson's algorithm for all-pairs shortest path problem

Wikipedia link [here](http://en.wikipedia.org/wiki/Johnson%27s_algorithm "Johnson's algorithm").

##### Description:

* Finds the shortest paths between all pairs of nodes in a weighted, directed graph.
* Allows negative edge weights, but **no** negative cycles.
* Works better on **sparse** graphs than Floyd-Warshall algorithm.
* Based on Bellman-Ford and Dijkstra algorithms.
* Complexity: `O(V²*log(V) + V*E)`

##### Steps:

1. Add a new node **s** to the graph, connected by **zero-weight** edges to each of the other nodes.

2. For each node **u** compute **h(u)** as shortest path from **s** to **u** using Bellman-Ford algorithm. If Bellman-Ford detects negative cycle, the algorithm stops.

3. Reweight every edge in graph by the following formula: `w(u,v) = w(u,v) + h(u) - h(v)`.

4. Remove **s**. For each node use Dijkstra's algorithm to find shortest paths to all the other nodes in the reweighted graph.

5. Finally, reweight back using formula: `d(u,v) = d(u,v) + h(v) - h(u)`, where **d(u,v)** is the shortest path from **u** to **v** computed in step 4.
