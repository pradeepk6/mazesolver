package mycompany;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class MazeSolver {

    SolveMaze solveMaze;

    public MazeSolver() {
    }

    public void setAlgorithm(SolveMaze solveMaze) {
        this.solveMaze = solveMaze;
    }

    public Maze readMazeFile(String path) throws IOException {
        Maze maze = new Maze();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            //read height and width
            String[] tokens = br.readLine().split(" ");
            maze.setWidth(Integer.valueOf(tokens[0]));
            maze.setHeight(Integer.valueOf(tokens[1]));

            //read start location
            tokens = br.readLine().split(" ");
            maze.setStartX(Integer.valueOf(tokens[0]));
            maze.setStartY(Integer.valueOf(tokens[1]));

            //read end location
            tokens = br.readLine().split(" ");
            maze.setEndX(Integer.valueOf(tokens[0]));
            maze.setEndY(Integer.valueOf(tokens[1]));

            // read the maze into an array
            char[][] mazeArray = new char[maze.getWidth()][maze.getHeight()];
            String line;
            int j = 0;
            while ((line = br.readLine()) != null) {
                char[] charArray = line.replaceAll("\\s+", "").toCharArray();
                for (int i = 0; i < charArray.length; i++) {
                    mazeArray[i][j] = charArray[i];
                }
                j++;
            }
            maze.setMazeArray(mazeArray);
        }
        return maze;
    }

    //generate Graph representation from mazeArray
    public Graph generateGraph(Maze maze) {

        char[][] mazeArray = maze.getMazeArray();
        int w = maze.getWidth();
        int h = maze.getHeight();
        int num_nodes = 0;
        Point p;
        int start_node = -1;
        int end_node = -1;
        HashMap<Point, Integer> nodeMap = new HashMap<>();


        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                if(mazeArray[i][j]!='1'){
                    p = new Point(i,j);
                    nodeMap.put(p, num_nodes);
                    if((i==maze.getStartX()) && (j==maze.getStartY()))
                        start_node = num_nodes;
                    if((i==maze.getEndX()) && (j==maze.getEndY()))
                        end_node = num_nodes;
                    num_nodes++;
                }
            }
        }
        maze.setNodeMap(nodeMap);
        maze.setStart_node(start_node);
        maze.setEnd_node(end_node);

        Graph g = new Graph<String>(num_nodes);
        int x, y;
        // Generating neighbours matrix
        for(int i=0; i<w; i++){
            for(int j=0; j<h; j++){
                if(mazeArray[i][j]!='1'){
                    //look in all four directions
                    x = nodeMap.get(new Point(i,j));
                    if((j>0) && mazeArray[i][j-1]!='1'){
                        y = nodeMap.get(new Point(i,j-1));
                        g.addEdge(x, y);
                    }
                    if((i>0) && mazeArray[i-1][j]!='1'){
                        y = nodeMap.get(new Point(i-1,j));
                        g.addEdge(x, y);
                    }
                    if((i+1<w) && mazeArray[i+1][j]!='1'){
                       y = nodeMap.get(new Point(i+1,j));
                        g.addEdge(x, y);
                    }
                    if((j+1<h) && mazeArray[i][j+1]!='1'){
                        y = nodeMap.get(new Point(i,j+1));
                        g.addEdge(x, y);
                    }
                    // wrapping
                    if((i==0) &&  mazeArray[w-1][j]!='1') {
                        y = nodeMap.get(new Point(w-1,j));
                        g.addEdge(x, y);
                    }
                    if((i+1==w) &&  mazeArray[0][j]!='1') {
                        y = nodeMap.get(new Point(0,j));
                        g.addEdge(x, y);
                    }
                    if((j==0) &&  mazeArray[i][h-1]!='1') {
                        y = nodeMap.get(new Point(i,h-1));
                        g.addEdge(x, y);
                    }
                    if((j+1==h) &&  mazeArray[i][0]!='1') {
                        y = nodeMap.get(new Point(i,0));
                        g.addEdge(x, y);
                    }
                }
            }
        }

        return g;
    }

    List<Point> findPath(Maze maze) {
     return this.solveMaze.solve(maze);
    }


    public void printMaze(Maze maze) {

        char[][] mazeArray = maze.getMazeArray();
        int w = maze.getWidth();
        int h = maze.getHeight();
        List<Point> path = maze.getSolution();
        StringBuilder mazeStr = new StringBuilder();
        for (int y=0; y<h; y++){
            for ( int x=0; x<w; x++) {

                if (mazeArray[x][y] == '1') {
                    mazeStr.append('#');
                } else if (path.contains(new Point(x,y))) {
                    if (x == maze.getStartX() && y == maze.getStartY()) mazeStr.append('S');
                    else if (x == maze.getEndX() && y == maze.getEndY())  mazeStr.append('E');
                    else mazeStr.append('X');
                } else {
                    mazeStr.append(' ');
                }
            }
            mazeStr.append("\n");
        }
        System.out.println(mazeStr);
    }

    public static void main(String args[]) throws Exception {

        String mazeDir = "src/test/resources/mazeSamples";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press one of the options below and Enter: ");
        System.out.println("  1 to solve all the mazes in test/resources directory.");
        System.out.println("  2 to enter the directory location of your own mazes");

        int choice = scanner.nextInt();
        if(choice<1 || choice>2) {
            System.out.println("Invalid input.");
            System.exit(1);
        }
        if (choice == 2) {
            mazeDir = scanner.next();
        }

        try (Stream<Path> filePathStream=Files.walk(Paths.get(mazeDir))) {
            filePathStream.forEach(file -> {
                if (Files.isRegularFile(file)) {
                    //time the program
                    double startTime = System.currentTimeMillis();

                    MazeSolver mazeSolver = new MazeSolver();
                    mazeSolver.setAlgorithm(new Algo());
                    Maze maze = null;
                    try {
                        maze = mazeSolver.readMazeFile(file.toString());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    maze.setMazeGraph( mazeSolver.generateGraph(maze));
                    List<Point> solutionPath = mazeSolver.findPath(maze);
                    double timeTaken = System.currentTimeMillis() - startTime;
                    maze.setSolution(solutionPath);



                    //if path found print the maze
                    if( !solutionPath.isEmpty()) {
                        System.out.println("\n");
                        System.out.println("Solution for file : " + file);
                        mazeSolver.printMaze(maze);
                        System.out.println("Time taken to solve the above maze in milliseconds: " + timeTaken);
                    } else System.out.println("Sorry could not solve the Maze.");
                }
            });
        }
    }
}