import java.util.*;
import java.io.*;

public class TSP {

    int[] rank, vertex;
    int size;
    double p;
    int counter;
    int vertices;

    public TSP(int size, int vertices) {
        rank = new int[size];
        vertex = new int[size + 1];
        this.size = size;
        this.vertices = vertices;
    }

    int find(int x) {

        while (x != vertex[x]) {
            vertex[x] = vertex[vertex[x]];
            x = vertex[x];
        }
        return vertex[x];

    }

    void union(int i, int j) {
        // Find vertexs of two sets
        int xRoot = find(i), yRoot = find(j);

        // if elements are in the same set, exit
        if (xRoot == yRoot)
            return;

        if (rank[xRoot] < rank[yRoot])
            vertex[xRoot] = yRoot;

        else if (rank[yRoot] < rank[xRoot])
            vertex[yRoot] = xRoot;

        else // if ranks are the same
        {
            // place y into x
            vertex[yRoot] = xRoot;
            // increment x rank
            rank[xRoot] = rank[xRoot] + 1;
        }

    }

    boolean isSameComponent(int vertex1, int vertex2) {
        return find(vertex1) == find(vertex2);
    }

    // attack 2d array generated from input to list of vertices for union find
    void buildGraph(ArrayList<int[]> numList) {

        System.out.println("\nSIZE: " + this.size);

        List<Integer> newVertex = new ArrayList<Integer>();
        for (int i = 0; i < numList.size(); i++) {
            newVertex.add(numList.get(i)[1]);
            newVertex.add(numList.get(i)[2]);
        }
        // i != j
        // for (int i = 0; i <= length - 2; i++) {
        // for (int j = i + 1; j <= length - 1; j++) {

        // // if i,j are in same component, add to counter
        // if (!isSameComponent(i, j)) {
        // union(i, j);
        // }
        // }

        // }

        for (int i = 0; i < newVertex.size(); i++) {

            System.out.println("Num List: " + newVertex.get(i));
            // System.out.println("Union " + numList.get(i)[1] + " => " +
            // numList.get(i)[2]);
            // union(numList.get(i)[1], numList.get(i)[2]);

        }
    }

    public static ArrayList<int[]> sortWeights(ArrayList<int[]> numList) {

        Collections.sort(numList, (int[] arr1, int[] arr2) -> {
            int weight1 = arr1[0];
            int weight2 = arr2[0];

            return weight1 - weight2;
        });
        return numList;
    }

    public static void main(String[] args) throws IOException {

        ArrayList<int[]> numList = new ArrayList<int[]>();

        try (Scanner reader = new Scanner(new File("input.txt"))) {

            // number of vertices
            int vertices = Integer.parseInt(reader.next());
            System.out.println("Vertices: " + vertices);
            while (reader.hasNext()) {

                int[] endpoint = new int[3];
                endpoint[1] = Integer.parseInt(reader.next());
                endpoint[2] = Integer.parseInt(reader.next());
                endpoint[0] = Integer.parseInt(reader.next());

                numList.add(endpoint);
            }

            TSP tsp = new TSP((numList.size() * 2), vertices);
            sortWeights(numList);
            tsp.buildGraph(numList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}