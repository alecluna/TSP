import java.util.*;
import java.io.*;
import java.lang.*;

class TSP {
    int totalVertices, E;
    Edge edge[];

    TSP(int vertices, int edges) {
        totalVertices = vertices;
        E = edges;
        edge = new Edge[E];
        // instantiate edges
        for (int i = 0; i < edges; i++)
            edge[i] = new Edge();
    }

    // Kruskals will contain groups of vertices
    class VerticeGroups {
        int rank, parent;
    };

    class Edge implements Comparable<Edge> {
        int source, destination, weight;

        // comparator function to sort weights
        public int compareTo(Edge nextWeight) {
            return this.weight - nextWeight.weight;
        };
    }

    // path compression
    int find(VerticeGroups forests[], int x) {
        while (x != forests[x].parent) {
            forests[x].parent = find(forests, forests[x].parent);
            x = forests[x].parent;
        }
        return forests[x].parent;
    }

    // union by rank
    void Union(VerticeGroups forests[], int x, int y) {
        int xRoot = find(forests, x);
        int yRoot = find(forests, y);

        if (forests[xRoot].rank < forests[yRoot].rank)
            forests[xRoot].parent = yRoot;

        else if (forests[xRoot].rank > forests[yRoot].rank)
            forests[yRoot].parent = xRoot;

        else {
            forests[yRoot].parent = xRoot;
            forests[xRoot].rank++;
        }
    }

    // start by sorting weights, then using union find to link
    // vertices/edges together into "forests". Repeat
    // until an MST with V - 1 vertices is found
    public void KruskalMST() {

        // instantiate an array of results
        Edge result[] = new Edge[totalVertices];
        // instantiate a group of vertices(forests)
        VerticeGroups forests[] = new VerticeGroups[totalVertices];

        for (int i = 0; i < totalVertices; i++) {
            result[i] = new Edge();
            // Edge.compareTo(edge);
        }

        // sort weights
        Arrays.sort(edge);

        // Allocate memory for creating V forests
        for (int i = 0; i < totalVertices; i++) {
            forests[i] = new VerticeGroups();
        }

        // store vertices, there will be v number of possible forests
        for (int v = 0; v < totalVertices; v++) {
            forests[v].parent = v;
            forests[v].rank = 0;
        }

        int cur = 0;
        int i = 0;
        // vertices - 1 to satisfty MST
        while (i < totalVertices - 1) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge nextEdge = new Edge();
            nextEdge = edge[cur++];

            if (find(forests, nextEdge.source) != find(forests, nextEdge.destination)) {
                // tallies number of edges are connected in forest
                result[i++] = nextEdge;
                Union(forests, find(forests, nextEdge.source), find(forests, nextEdge.destination));
            }
        }

        // no repeats
        HashSet<Integer> finalOutput = new HashSet<Integer>();

        for (int x = 0; x < i; x++) {
            finalOutput.add(result[x].source);
            finalOutput.add(result[x].destination);
        }

        for (int k : finalOutput) {
            System.out.print(k + " ");
        }
    }

    public static void main(String[] args) throws IOException {

        // read file
        try (Scanner reader = new Scanner(new File("input.txt"))) {

            int vertices = Integer.parseInt(reader.next());

            if (vertices == 0) {
                System.out.println("0 Vertices given ... Terminating program");
                System.exit(0);
            }

            int edges = vertices + 1;
            TSP TSP = new TSP(vertices, edges);
            int counter = 0;

            while (reader.hasNext()) {

                TSP.edge[counter].source = Integer.parseInt(reader.next());
                TSP.edge[counter].destination = Integer.parseInt(reader.next());
                TSP.edge[counter].weight = Integer.parseInt(reader.next());
                counter++;
            }
            // begin Kruskal MST
            TSP.KruskalMST();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
