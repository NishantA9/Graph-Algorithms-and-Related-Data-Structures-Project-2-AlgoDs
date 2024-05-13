//Nishant Acharekar - 801363902 & Ashutosh Zawar (801366153)

package Proj2AlgoDs;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class MinimumSpanningTreeAlgorithm {
    // File reading components
    private Scanner scanFile;
    private FileReader readFile;
    private String currentData;
    
    // Graph properties
    private int noOfVertices, noOfEdges, currentIndex;
    private Edge[] currentAnswer, currentEdges;
   
    // Constant for vertex indexing
    private final String VERTEX_INDEX_TRACKING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // Edge class to represent graph edges
    static class Edge implements Comparable<Edge> {
        int currentSource, currentDestination, currentWeight;
        public int compareTo(Edge edge) {
            return this.currentWeight - edge.currentWeight;
        }
    }
    
    // Subset class for union-find operations
    static class SubSet {
        int currentParent, currentRank;
    }
    
    // Main algorithm execution
    public void executeMainAlgorithm() {

        // Start measuring the runtime
        long startTime = System.nanoTime();

        // Initialize answer array
        currentAnswer = new Edge[noOfVertices];
        currentIndex = 0;
        
        for (int j = 0; j < noOfVertices; j++)
            currentAnswer[j] = new Edge();
        // Sort edges based on weight
        Arrays.sort(currentEdges);
        
        // Initialize subsets for union-find
        SubSet[] subSets = new SubSet[noOfVertices];
        for (int j = 0; j < noOfVertices; j++)
            subSets[j] = new SubSet();
        
        // Initialize subsets with parent and rank
        for(int i = 0; i < noOfVertices; i++) {
            subSets[i].currentParent = i;
            subSets[i].currentRank = 0;
        }
        
        // Kruskal's algorithm to find MST
        int j = 0;
        while (currentIndex < noOfVertices - 1) {
            Edge nextEdge;
            nextEdge = currentEdges[j++];
            int x = findMST(subSets, nextEdge.currentSource);
            int y = findMST(subSets, nextEdge.currentDestination);
            if (x != y) {
                currentAnswer[currentIndex++] = nextEdge;
                unionMST(subSets, x, y);
            }
        }
        // End measuring the runtime
        long endTime = System.nanoTime();

        // Calculate and print the runtime
        double runtimeInMillis = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
        System.out.println("The runtime of this algorithm is: " + runtimeInMillis + " milliseconds");
    
    }
    
    // Read input file to populate graph data
    public void readIpFile(String file) throws Exception {
        File inputFile = new File(file);
        readFile = new FileReader(inputFile);
        scanFile = new Scanner(readFile);
        
        // Read number of vertices and edges
        noOfVertices = scanFile.nextInt();
        noOfEdges = scanFile.nextInt();
        scanFile.next();
        System.out.println("Number of Vertices are: " + noOfVertices);
        System.out.println("Number of Edges are: " + noOfEdges);
        
     // Initialize currentEdges array
        currentEdges = new Edge[noOfEdges];
        for (int i = 0; i < noOfEdges; i++)
            currentEdges[i] = new Edge();
        
     // Populate currentEdges with edge data
        for (int i = 0; i < noOfEdges; i++) {
            int src = VERTEX_INDEX_TRACKING.indexOf(scanFile.next().charAt(0));
            int dest = VERTEX_INDEX_TRACKING.indexOf(scanFile.next().charAt(0));
            int currentWeight = scanFile.nextInt();
            currentEdges[i].currentSource = src;
            currentEdges[i].currentDestination = dest;
            currentEdges[i].currentWeight = currentWeight;
        }
    }
    
    // Union operation in union-find
    public void unionMST(SubSet[] subSets, int x, int y) {
        int X = findMST(subSets, x);
        int Y = findMST(subSets, y);
        if(subSets[X].currentRank > subSets[Y].currentRank)
            subSets[Y].currentParent = X;
        else if (subSets[X].currentRank < subSets[Y].currentRank)
            subSets[X].currentParent = Y;
        else {
            subSets[Y].currentParent = X;
            subSets[X].currentRank++;
        }
    }
    
    // Find operation in union-find with path compression
    public int findMST(SubSet[] subSets, int i) {
        if(subSets[i].currentParent != i)
            subSets[i].currentParent = findMST(subSets, subSets[i].currentParent);
        return subSets[i].currentParent;
    }
    
    // Displaying the MST edges and total cost
    public void displayOp() {
        System.out.println("Minimum Spanning Tree- ");
        int totalCost = 0;
        for(int i = 0; i < currentIndex; i++) {
            System.out.println(VERTEX_INDEX_TRACKING.charAt(currentAnswer[i].currentSource) + " -> " + VERTEX_INDEX_TRACKING.charAt(currentAnswer[i].currentDestination) + "  ,   Cost- " + currentAnswer[i].currentWeight);
            totalCost += currentAnswer[i].currentWeight;
        }
        System.out.println("Total Cost of the Minimum Spanning Tree is: " + totalCost);
    }
}

