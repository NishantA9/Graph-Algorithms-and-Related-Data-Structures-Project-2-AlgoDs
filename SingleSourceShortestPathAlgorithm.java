//Nishant Acharekar - 801363902 & Ashutosh Zawar (801366153)

package Proj2AlgoDs;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class SingleSourceShortestPathAlgorithm {
	
    // FileReader and Scanner objects for reading input file
    private FileReader readFile;
    private Scanner scanFile;
    
    // String to represent vertices using letters
    private final String VERTEX_INDEX_TRACKING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // Data structures for graph representation and algorithm variables
    private List<List<Node>> ipGraph; // Adjacency list to represent the graph
    private int sourceVertex, noOfVertices, noOfEdges; // Number of vertices, edges, and source vertex
    private int[] overallCost; // Array to store overall cost to reach each vertex from source
    private int[] currentParent; // Array to store parent vertex for each vertex in shortest path
    private String currentData; // String to hold current line of data from input file
    private boolean isDirectedGraph;  // Flag to indicate if the graph is directed or undirected
    
    // Method to read input file and populate graph data structures
    public void readIpFile(String file) throws Exception {
    File inputFile = new File(file);
    readFile = new FileReader(inputFile);
    scanFile = new Scanner(readFile);
    currentData = scanFile.nextLine();
    
    // Read first line to determine graph type, number of vertices, and edges
    if(currentData != null) {
        String[] t = currentData.split(" ");
        if(t[2].equals("U")) {
            isDirectedGraph = false;
            System.out.println("Input Graph is an Undirected Graph");
        }
        else {
            isDirectedGraph = true;
            System.out.println("Input Graph is a Directed Graph");
        }
        
        // Extract number of vertices and edges
        noOfVertices = Integer.parseInt(t[0]);
        noOfEdges = Integer.parseInt(t[1]);
        
        System.out.println("Number of Vertices are: " + noOfVertices);
        System.out.println("Number of Edges are: " + noOfEdges);
        
        // Initialize adjacency list for graph representation
        ipGraph = new ArrayList<List<Node>>();
        for (int i = 0; i < noOfVertices; i++)
            ipGraph.add(new ArrayList<Node>());
        
        // Read edge data and populate adjacency list
        for (int i = 0; i < noOfEdges; i++) {
            currentData = scanFile.nextLine();
            t = currentData.split(" ");
            int x = VERTEX_INDEX_TRACKING.indexOf(t[0].charAt(0));
            int y = VERTEX_INDEX_TRACKING.indexOf(t[1].charAt(0));
            ipGraph.get(x).add(new Node(y, Integer.parseInt(t[2])));
            
            // Add reverse edge for undirected graph
            if(!isDirectedGraph)
                ipGraph.get(y).add(new Node(x, Integer.parseInt(t[2])));
        }

        // Read source vertex if provided, otherwise set default source vertex
        if(scanFile.hasNext()) {
            currentData = scanFile.nextLine();
            sourceVertex = VERTEX_INDEX_TRACKING.indexOf(currentData.charAt(0));
            System.out.println("Given source vertex is " + currentData.charAt(0));
        }
        else {
            sourceVertex = 0;
            System.out.println("Source vertex is not provided. Let the source vertex be: " + VERTEX_INDEX_TRACKING.charAt(sourceVertex));
        }
    }
}

    // Method to execute the main Dijkstra's Algorithm
    public void executeMainAlgorithm() {
    if(ipGraph == null) {
        System.out.println("Graph is not initialized. Please read the input file first.");
        return;
    }
    
    // Get the start time
    long startTime = System.nanoTime();

    // Initialization of arrays
    currentParent = new int[noOfVertices];
    overallCost = new int[noOfVertices];
    
    // Setting initial values for overallCost and currentParent arrays
    for (int i = 0; i < noOfVertices; i++)
        overallCost[i] = Integer.MAX_VALUE;
    overallCost[sourceVertex] = 0;
    currentParent[sourceVertex] = -1;
    
    // Set to keep track of vertices included in the shortest path tree
    Set<Integer> cloud = new HashSet<Integer>();
    
    // Priority queue to prioritize vertices based on their cost
    PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(noOfVertices, new Node());
    
    // Adding source vertex to priority queue
    priorityQueue.add(new Node(sourceVertex, 0));
    
    // Dijkstra's Algorithm main loop
    while (!priorityQueue.isEmpty()) {
        int a = priorityQueue.remove().currentNode;
        cloud.add(a);

        for (Node neighbor : ipGraph.get(a)) {
            int b = neighbor.currentNode;
            if (!cloud.contains(b)) {
                int c = neighbor.currentWeight;
                
                // Update overall cost and parent if a shorter path is found
                if (overallCost[a] + c < overallCost[b]) {
                    overallCost[b] = overallCost[a] + c;
                    currentParent[b] = a;
                    priorityQueue.add(new Node(b, overallCost[b]));
                }
            }
        }
    }

    // Get the end time
    long endTime = System.nanoTime();

    // Calculate and print the runtime
    double runtimeInMillis = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
    System.out.println("The runtime of this algorithm is: " + runtimeInMillis + " milliseconds");
}

    // Method to display shortest paths and path costs
    public void displayOp() {
        System.out.println("All Shortest Paths and Path Costs are as follows: ");
        for (int i = 0; i < noOfVertices; i++) {
            if(i != sourceVertex) {
                int vertex = i;
                String temp = "";
                
                // Constructing shortest path string
                while (currentParent[vertex] != -1) {
                    temp = " -> " + VERTEX_INDEX_TRACKING.charAt(vertex) + temp;
                    vertex = currentParent[vertex];
                }
                
                // Displaying shortest path and path cost
                System.out.print("\n" + VERTEX_INDEX_TRACKING.charAt(sourceVertex) + temp + "  ,   Cost-");
                System.out.print(overallCost[i]);
            }
        }
        System.out.println();
    }
}

//Node class to represent vertices and their weights
class Node implements Comparator<Node> {
    int currentNode, currentWeight;
    
    // Default constructor
    Node() {}
    Node(int currentNode, int currentWeight) {
        this.currentNode = currentNode;
        this.currentWeight = currentWeight;
    }
    
    // Compare method to compare nodes based on their weights
    public int compare(Node n1, Node n2) {
        if (n1.currentWeight < n2.currentWeight)
            return -1;
        if (n1.currentWeight > n2.currentWeight)
            return 1;
        return 0;
    }
}
