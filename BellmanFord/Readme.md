#### Bellman-Ford algorithm for single-source shortest path problem

Wikipedia link [here](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm "Bellman-Ford algorithm").

##### Description:

* Given a source node, finds the shortest paths from it to all the other nodes in the graph.
* Designed for directed weighted graphs.
* Works for negative edge weights, but **not** for negative cycles.
* Detects negative cycles in the graph and reports their existance.
* Slower than Dijkstra's algorithm, but more versatile.
* Complexity: `O(V*E)`
