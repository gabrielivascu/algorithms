#### Floyd-Warshall algorithm for all-pairs shortest path problem

Wikipedia link [here](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm "Floyd-Warshall algorithm").

##### Description:

* Finds the shortest paths between all pairs of nodes in a weighted graph.
* Works for both directed and undirected graphs.
* Allows negative edge weights, but **no** negative cycles.
* Faster than Johnson's algorithm for dense graphs.
* Uses a dynamic programming approach.
* Complexity: `O(V³)`
