import java.util.*;
import java.io.*;

public class TSP {

    int[] rank, vertex;
    int size;
    double p;
    int counter;

    public TSP(int size) {
        rank = new int[size];
        vertex = new int[size];
        this.size = size;
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

    // attack 2d array generated from input to list of vertices for union find
    public static void BuildGraph(ArrayList<int[]> numList, int vertexCount, int length) {

        for (int i = 0; i <= numList.size() - 1; i++) {

            System.out.println(
                    "Num List: " + numList.get(i)[0] + " Src: " + numList.get(i)[1] + " => " + numList.get(i)[2]);
        }

        // for (int i = 0; i <= numList.size() - 1; i++) {

        // }

    }

    public static ArrayList<int[]> sortWeights(ArrayList<int[]> numList) {

        Collections.sort(numList, (int[] arr1, int[] arr2) -> {
            int weight1 = arr1[0];
            int weight2 = arr2[0];

            if (weight1 == weight2) {
                return 0;
            } else if (weight1 < weight2) {
                return -1;
            } else
                return 1;
        });
        return numList;
    }

    public static void main(String[] args) throws IOException {
        Scanner reader = null;

        ArrayList<int[]> numList = new ArrayList<int[]>();

        try {
            reader = new Scanner(new File("input.txt"));

            // number of vertices
            int vertices = Integer.parseInt(reader.next());
            System.out.println(vertices);
            while (reader.hasNext()) {

                int[] endpoint = new int[3];
                endpoint[1] = Integer.parseInt(reader.next());
                endpoint[2] = Integer.parseInt(reader.next());
                endpoint[0] = Integer.parseInt(reader.next());

                numList.add(endpoint);
            }

            TSP tsp = new TSP(numList.size());
            sortWeights(numList);
            BuildGraph(numList, vertices, numList.size());

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            if (reader != null) {
                reader.close();
            }

        }
    }
}