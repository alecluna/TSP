import java.util.*;
import java.io.*;
import java.lang.*;

class TSP {
    class Edge implements Comparable<Edge> {
        int source, destination, weight;

        // compartor function to sort weights, TODO refactor
        public int compareTo(Edge nextWeight) {
            if (this.weight == nextWeight.weight)
                return 0;
            else if (this.weight < nextWeight.weight)
                return -1;
            else
                return 1;
        }
    };

    // A class to represent a subset for union-find
    class verticesGroups {
        int parent, rank;
    };

    int V, E; // V-> no. of vertices & E->no.of edges
    Edge edge[]; // collection of all edges

    // Creates a TSP with V vertices and E edges
    TSP(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    // A utility function to find set of an element i
    // (uses path compression technique)
    int find(verticesGroups subsets[], int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y
    // (uses union by rank)
    void Union(verticesGroups subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        // (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

        // If ranks are same, then make one as root and increment
        // its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public void KruskalMST() {
        Edge result[] = new Edge[V]; // expected output
        int e = 0; // An index variable, used for result[]
        int i = 0; // An index variable, used for sorted edges
        for (i = 0; i < V; ++i)
            result[i] = new Edge();

        // Step 1: Sort all the edges in non-decreasing order of their
        // weight. If we are not allowed to change the given TSP, we
        // can create a copy of array of edges
        Arrays.sort(edge);

        // Allocate memory for creating V subsets
        verticesGroups subsets[] = new verticesGroups[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new verticesGroups();

        // Create V subsets with single elements
        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = new Edge();
            next_edge = edge[i++];

            int x = find(subsets, next_edge.source);
            int y = find(subsets, next_edge.destination);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        System.out.println("MST using Kruskals");
        HashSet<Integer> finalOutput = new HashSet<Integer>();

        for (i = 0; i < e; ++i) {
            System.out.println(result[i].source + " -- " + result[i].destination);
            finalOutput.add(result[i].source);
            finalOutput.add(result[i].destination);
        }

        for (int k : finalOutput) {
            System.out.println(k);
        }
    }

    public static void main(String[] args) {

        try (Scanner reader = new Scanner(new File("input.txt"))) {

            int V = Integer.parseInt(reader.next());

            if (V == 0) {
                System.out.println("0 Vertices given...exiting program");
            }

            int E = V + 1;
            System.out.println("Vertices V = " + V);
            System.out.println("Edges E = " + E);

            TSP TSP = new TSP(V, E);
            int counter = 0;
            while (reader.hasNext()) {

                TSP.edge[counter].source = Integer.parseInt(reader.next());
                TSP.edge[counter].destination = Integer.parseInt(reader.next());
                TSP.edge[counter].weight = Integer.parseInt(reader.next());
                counter++;
            }

            TSP.KruskalMST();
            System.out.println("done ✅");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
