//Nishant Acharekar - 801363902 & Ashutosh Zawar (801366153)


package Proj2AlgoDs;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class DFSAlgorithm {
    
	// File reading components listed below
    private FileReader readFile;
    private Scanner scanFile;
    
    // Constants 
    private final String VERTEX_INDEX_TRACKING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // Below is the Graph representation
    private List<List<Node>> ipGraph;
    private int noOfVertices, noOfEdges;
    private boolean isDirectedGraph;
    
    // Mapping between vertices and characters
    private Map<Integer, Character> vertexToCharMapping = new HashMap<>();
    private Map<Character, Integer> charToVertexMapping = new HashMap<>();
    
    // Data for topological ordering and cycle detection
    private List<Character> topologicalOrder = new ArrayList<>();
    private List<List<Character>> cycles = new ArrayList<>();
    private Stack<Character> currentCycleStack = new Stack<>();
    
    // Array to track visitedVerticesTracking vertices during DFS
    private int[] visited;
    
    // Reset all data structures
    public void reset() {
        visited = new int[noOfVertices];
        topologicalOrder.clear();
        cycles.clear();
        currentCycleStack.clear();
    }

  	// Execute the DFS algorithm
    
    public void executeMainAlgorithm() {
    visited = new int[noOfVertices];
    boolean[] inCycle = new boolean[noOfVertices];
    LinkedList<Integer> path = new LinkedList<>();

    // Start measuring the runtime
    long startTime = System.nanoTime();

       for (int i = 0; i < noOfVertices; i++) {
           if (visited[i] == 0) {
            	dfs1(i, visited, new LinkedList<>());
            }
        }
    
         // End measuring the runtime
         long endTime = System.nanoTime();
   
    //  //Displaying the results
    //     if (cycles.isEmpty()) {
    //         System.out.println("Topological Sorting Sequence (Directed Acyclic Graph):");
    //         for (int i = topologicalOrder.size() - 1; i >= 0; i--) {
    //             System.out.print(topologicalOrder.get(i) + " ");
    //         }
    //         System.out.println();
    //     } else {
    //         System.out.println("The directed graph contains cycles (Directed Cyclic Graph):");
    //         for (List<Character> cycle : cycles) {
    //             // Add the starting vertex at the end of the cycle
    //             List<Character> extendedCycle = new ArrayList<>(cycle);
    //              extendedCycle.add(cycle.get(1));
    //             System.out.println("Cycle: " + cycle.toString() + ", Length: " + cycle.size());
    //         }
    //     }

    // Displaying the results
if (cycles.isEmpty()) {
    System.out.println("Topological Sorting Sequence (Directed Acyclic Graph):");
    for (int i = topologicalOrder.size() - 1; i >= 0; i--) {
        System.out.print(topologicalOrder.get(i) + " ");
    }
    System.out.println();
} else {
    System.out.println("The directed graph contains cycles (Directed Cyclic Graph):");
    for (List<Character> cycle : cycles) {
        // Add the starting vertex at the end of the cycle
        List<Character> extendedCycle = new ArrayList<>(cycle);
        extendedCycle.add(cycle.get(0));  // Add the starting vertex
        System.out.println("Cycle: " + extendedCycle.toString() + ", Length: " + extendedCycle.size());
    }
}

        // Calculate and print the runtime
        double runtimeInMillis = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
        System.out.println("The runtime of this algorithm is: " + runtimeInMillis + " milliseconds");
    }
    

    // Depth First Search traversal 
    LinkedList<Integer> path = new LinkedList<Integer>();
    private boolean dfs1(int vertex, int[] inCycle, LinkedList<Character> cyclePath) {
        currentCycleStack.push(VERTEX_INDEX_TRACKING.charAt(vertex));

        if(inCycle[vertex] == 1) {
            cycles.add(new LinkedList<>(cyclePath));
            return false;
        }
        if(inCycle[vertex] == 2) {
            return true;
        }
        cyclePath.add(VERTEX_INDEX_TRACKING.charAt(vertex));
        inCycle[vertex] = 1;
        for (Node neighbor : ipGraph.get(vertex)) {
            if (!dfs1(neighbor.currentNode, inCycle, cyclePath)) {
                return false;
            } 
        }
        inCycle[vertex] = 2;
        path.add(vertex);
        topologicalOrder.add(VERTEX_INDEX_TRACKING.charAt(vertex));
        return true;
    }

    
    // Read input graph from a file
    public void readIpFile(String file) throws Exception {
        File inputFile = new File(file);
        readFile = new FileReader(inputFile);
        scanFile = new Scanner(readFile);
        
        // Read graph type and validate
        String data = scanFile.nextLine();
        String[] t = data.split(" ");

        if (t.length > 2 && t[2].equals("U")) {
            throw new IllegalArgumentException("Undirected graphs are not supported for this problem. "
            		+ "/n Please Run the program again with a Directed Graph as Input!!!");
        }
        
        // Initialize graph properties
        isDirectedGraph = true;
        System.out.println("Input Graph is a Directed Graph");
        noOfVertices = Integer.parseInt(t[0]);
        noOfEdges = Integer.parseInt(t[1]);
        
        // Check if the graph meets the minimum requirements
        if(noOfVertices < 15 || noOfEdges < 25) {
            throw new IllegalArgumentException("The graph does not meet the minimum requirements. Please provide a correct Graph!!!");
        }
        
        System.out.println("Number of Vertices are: " + noOfVertices);
        System.out.println("Number of Edges are: " + noOfEdges);
        
        // Initialize graph data structures
        ipGraph = new ArrayList<>();
        for (int i = 0; i < noOfVertices; i++) {
            ipGraph.add(new ArrayList<>());
            vertexToCharMapping.put(i, VERTEX_INDEX_TRACKING.charAt(i));
            charToVertexMapping.put(VERTEX_INDEX_TRACKING.charAt(i), i);
        }
        
        // Populate graph edges
        for (int i = 0; i < noOfEdges; i++) {
            data = scanFile.nextLine();
            t = data.split(" ");
            int x = VERTEX_INDEX_TRACKING.indexOf(t[0].charAt(0));
            int y = VERTEX_INDEX_TRACKING.indexOf(t[1].charAt(0));
            ipGraph.get(x).add(new Node(y, Integer.parseInt(t[2])));
            if (!isDirectedGraph) {
                ipGraph.get(y).add(new Node(x, Integer.parseInt(t[2])));
            }
        }
        
        // Read source vertex if provided
        if (scanFile.hasNext()) {
            data = scanFile.nextLine();
            char sourceVertex = data.charAt(0);
            int sourceVertexIndex = charToVertexMapping.get(sourceVertex);
            System.out.println("Given source vertex is: " + sourceVertex);
        } else {
            System.out.println("Source vertex is not provided. Please Provide a source vertex in Input File!!!");
        }
    }
    
    // Displaying the output
    public void displayOp() {
        // Output is displayed within the executeMainAlgorithm() method
    }

    // Node class to represent graph vertices
    private static class Node {
        int currentNode, currentWeight;

        Node(int currentNode, int currentWeight) {
            this.currentNode = currentNode;
            this.currentWeight = currentWeight;
        }
    }
}
