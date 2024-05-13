//Nishant Acharekar - 801363902 & Ashutosh Zawar (801366153)

package Proj2AlgoDs;
import java.util.Scanner;

public class Proj2AlgoDsMain {
    public static void main(String[] args) throws Exception{
    	// Create objects for the three algorithms.
        SingleSourceShortestPathAlgorithm problem1 = new SingleSourceShortestPathAlgorithm();
        MinimumSpanningTreeAlgorithm problem2 = new MinimumSpanningTreeAlgorithm();
        DFSAlgorithm problem3 = new DFSAlgorithm();  
        
     // A scanner object for user input.
        Scanner scanner = new Scanner(System.in);
        int myChoice;
        int ipFileChoice;
        String ipFilePath;
        boolean stop = false;
        
        // Display welcome message
        System.out.println("\n *** Welcome to (ITCS: 6114/ 8114) Algorithms and Data Structures - Project 2 ***");
        System.out.println("\n *** A project by Nishant Acharekar (801363902) and Ashutosh Zawar (801366153) ***");
        do {
            // Display menu options
            System.out.println();
            System.out.println("Choose any one of the problems given below:");
            System.out.println("1. Single Source Shortest Path");
            System.out.println("2. Minimum Spanning Tree");
            System.out.println("3. DFS - Topological Sorting and Cycles");
            System.out.println("4. Exit");
            
            // Get user choice
            myChoice = scanner.nextInt();
            switch (myChoice) {
                case 1:
                    // Single Source Shortest Path problem
                    System.out.println("Select any one input file from the following:");
                    System.out.println("1. Input-1 (Undirected Graph)");
                    System.out.println("2. Input-2 (Undirected Graph)");
                    System.out.println("3. Input-3 (Directed Graph)");
                    System.out.println("4. Input-4 (Directed Graph)");
                    
                    // Get input file choice
                    ipFileChoice = scanner.nextInt();
                    // Validate input file choice
                    if(ipFileChoice < 1 || ipFileChoice > 4) {
                        System.out.println("Invalid option. Please enter valid option !");
                        break;
                    }
                    
                    // Get input file path based on choice
                    ipFilePath = getIpFilePath(ipFileChoice, 1);
                    
                    // Read input file, execute algorithm and display output
                    problem1.readIpFile(ipFilePath);
                    problem1.executeMainAlgorithm();
                    problem1.displayOp();
                    break;
                    
                case 2:
                    // Minimum Spanning Tree problem
                    System.out.println("Select any one input file from the following:");
                    System.out.println("1. Input-1 (Undirected Graph)");
                    System.out.println("2. Input-2 (Undirected Graph)");
                    System.out.println("3. Input-3 (Undirected Graph)");
                    System.out.println("4. Input-4 (Undirected Graph)");
                    ipFileChoice = scanner.nextInt();
                    if(ipFileChoice < 1 || ipFileChoice > 4) {
                        System.out.println("Invalid option. Please enter valid option !");
                        break;
                    }
                    ipFilePath = getIpFilePath(ipFileChoice, 2);
                    problem2.readIpFile(ipFilePath);
                    problem2.executeMainAlgorithm();
                    problem2.displayOp();
                    break;
                    
                case 3:
                    // DFS - Topological Sorting and Cycles problem
                    System.out.println("Select any one input file from the following:");
                    System.out.println("1. Input-1 (Directed Graph (Acyclic))");
                    System.out.println("2. Input-2 (Directed Graph (Cyclic))");
                    System.out.println("3. Input-3 (Directed Graph (Acyclic))");
                    System.out.println("4. Input-4 (Directed Graph (Cyclic))");
                    ipFileChoice = scanner.nextInt();
                    if(ipFileChoice < 1 || ipFileChoice > 4) {
                        System.out.println("Invalid option. Please enter valid option !");
                        break;
                    }
                    ipFilePath = getIpFilePath(ipFileChoice, 3);
                    problem3.readIpFile(ipFilePath);
                    problem3.executeMainAlgorithm();
//                    problem3.displayOp();
                    problem3.reset();
                    break;
                    
                case 4:
                    // Exit the program
                    stop = true;
                    break;
                    
                default:
                    // Invalid choice
                    System.out.println("Invalid option. Please enter valid option !");
                    break;
            }
        } while (!stop);
    }

    
 // Method for determining input file path based on user selection and problem kind
    public static String getIpFilePath(int myChoice, int problem) {
        String path = "";
        
        // Adjust choice based on problem type
        if(problem == 2)
            myChoice += 4;
        else if (problem == 3)
            myChoice += 8;
        
        // Assign file path based on adjusted choice
        switch (myChoice) {
            case 1:
                path = "./InputFiles/input1.txt";
                break;
            case 2:
                path = "./InputFiles/input2.txt";
                break;
            case 3:
                path = "./InputFiles/input3.txt";
                break;
            case 4:
                path = "./InputFiles/input4.txt";
                break;
            case 5:
                path = "./InputFiles/input5.txt";
                break;
            case 6:
                path = "./InputFiles/input6.txt";
                break;
            case 7:
                path = "./InputFiles/input7.txt";
                break;
            case 8:
                path = "./InputFiles/input8.txt";
                break;
            case 9:
                path = "./InputFiles/input9.txt";
                break;
            case 10:
                path = "./InputFiles/input10.txt";
                break;
            case 11:
                path = "./InputFiles/input11.txt";
                break;
            case 12:
                path = "./InputFiles/input12.txt";
                break;
            default:
                break;
        }
        return path;
    }
}
