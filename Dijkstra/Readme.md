#### Dijkstra's algorithm for shortest path problem

Wikipedia link [here](http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm "Dijkstra's algorithm").

##### Description:

* Given a source node, finds the shortest paths from it to all the other nodes in the graph.
* It does **not** work on negative edge weights, also not on negative cycles.
* Works for both directed and undirected graphs.
* Complexity: `O(E*log(V))` if using Binary Heap as a priority queue, `O(V*log(V) + E)` if using Fibonacci Heap.
