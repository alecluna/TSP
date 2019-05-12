Implementing the 2-approximation Algorithm for TSP

Use the algorithm found in CLRS 35.2.1 and in the Piazza Note 8. Use Kruskal’s MST algorithm, at the end of Note 5, instead of Prim’s, using Union-Find and Heap data structures that you implement.

Your program should read from standard input a sequence of integers separated by whitespace. The first integer will be n, the number of vertices in the graph. (Vertices will be labeled 0 through n − 1.) There will then be n(n − 1)/2 triples of integers, each representing an edge. The first two integers of each triple represent the edge’s endpoints and the third number represents the positive edge weight.

The input may be assumed to be correct, and represent a complete, simple, undirected graph with all
triangles of edges satisfying the triangle inequality. Your program should output each vertex exactly
once with a space separating each vertex, representing the sequence of vertices in your tour (it is implied
that the tour goes back to the starting point at the end). Your program should output nothing else. For exampl:

> echo "3 0 1 1 2 1 2 2 3 3" | TSP
> 0 1 2
