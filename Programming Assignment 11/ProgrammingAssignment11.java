import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * This is a programming Assignment for Dr. Biaz's Introduction to Algorithms Class
 * Authors: Matthew Freestone and Melvin Moreno
 */
public class ProgrammingAssignment11 {
    //Implement Bell-Man Ford's Algorithm (Work Cited: GeeksForGeeks)
    class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    ;

    int verticesCount, edgeCount;
    ArrayList<Edge> edgeList;

    //Creates a graph with V vertices and E edges
    ProgrammingAssignment11() {
        verticesCount = 0;
        edgeCount = 0;
        edgeList = new ArrayList<Edge>();
    }

    void addEdge(int src, int dest, int weight) {
        edgeList.add(new Edge(src, dest, weight));
        edgeCount++;
    }

    // print edges
    String printEdges() {
        String s = "";
        for (int i = 0; i < edgeCount; i++) {
            s += edgeList.get(i).src + " " + edgeList.get(i).dest + " " + edgeList.get(i).weight + "\n";
        }
        return s;
    }

    // The main function that finds shortest distances from src
    // to all other vertices using Bellman-Ford algorithm. The
    // function also detects negative weight cycle
    String BellmanFord(Graph graph, int src) {
        int V = graph.verticesCount, E = graph.edgeCount;
        int dist[] = new int[V];
        int parents[] = new int[V];

        //Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
            parents[i] = -1;
        }
        dist[src] = 0;
        parents[src] = src;

        //Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at most |V| - 1 edges
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graph.edgeList.get(j).src;
                int v = graph.edgeList.get(j).dest;
                int weight = graph.edgeList.get(j).weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parents[v] = u;
                }
            }
        }

        //Step 3: Check for negative-weight cycles. The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        // path, then there is a cycle.
        for (int j = 0; j < E; ++j) {
            int u = graph.edgeList.get(j).src;
            int v = graph.edgeList.get(j).dest;
            int weight = graph.edgeList.get(j).weight;
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("Graph contains negative weight cycle");
                return "Negative Cycles Found.";
            }
        }

        String output = "";
        for (int i = 0; i < V; ++i) {
            if (i == src) {
                continue;
            }
            output += i + ": " + getPath(i, src, parents) + "\n";
        }
        return output;
    }

    // get path to src
    String getPath(int src, int dest, int[] parents) {

        // follow parents to get path from a dest to src
        String path = "";
        int u = src;
        while (u != dest) {
            path += u + " ";
            u = parents[u];
            if (u == -1) {
                return "No Path Found.";
            }
        }
        // reverse path to get from src to dest
        String[] pathArr = path.split(" ");
        String reversePath = "";
        for (int i = pathArr.length - 1; i >= 0; i--) {
            reversePath += pathArr[i] + " ";
        }

        return reversePath;
    }

    //Driver method to test above function
    public static void main(String[] args) {
        //Asks for file name
        System.out.print("Enter the file name with extension: ");
        Scanner input = new Scanner(System.in);
        try {
            File file = new File(input.nextLine());
            input = new Scanner(file);
        } catch (NullPointerException e) {
            input.close();
            System.out.println("File not found.");
            System.exit(0);
        } catch (FileNotFoundException e) {
            input.close();
            System.out.println("File not found.");
            System.exit(0);
        }
        Graph graph = new Graph();

        // Throw away first value
        input.nextLine();

        //Spews out file content line by line
        while (input.hasNextLine()) {
            String line = input.nextLine();
            graph.verticesCount++;

            String[] linedata = line.split(" ");
            int origin = Integer.parseInt(linedata[0]);

            for (int i = 1; i < linedata.length; i++) {
                String[] currentData = linedata[i].split(",");
                int destination = Integer.parseInt(currentData[0]);
                int weight = Integer.parseInt(currentData[1]);
                graph.addEdge(origin, destination, weight);
            }
        }
        input.close();
        // System.out.println(graph.printEdges());
        System.out.print("Pick a src vertex: ");
        input = new Scanner(System.in);
        int src = input.nextInt();
        input.close();
        String result = graph.BellmanFord(graph, src);
        System.out.println(result);


        File output = new File("outputShortestPaths.txt");
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(output);
            outputStream.write(result.getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error when writing to file.");
            System.exit(0);
        }

    }
}

