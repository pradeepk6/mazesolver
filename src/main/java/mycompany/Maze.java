package mycompany;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Maze {

    //(x,y) location of start
    private int startX;
    private int startY;

    //(x,y) location of end
    private int endX;
    private int endY;

    //height of mazeArray
    private int height;
    //width of mazeArray
    private int width;

    //array representation of the Maze
    private char[][] mazeArray;

    // graph representation of the Maze
    private Graph mazeGraph;

    HashMap<Point, Integer> nodeMap = new HashMap<>();
    int end_node;
    int start_node;

    public HashMap<Point, Integer> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(HashMap<Point, Integer> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public int getStart_node() {
        return start_node;
    }

    public void setStart_node(int start_node) {
        this.start_node = start_node;
    }

    public int getEnd_node() {
        return end_node;
    }

    public void setEnd_node(int end_node) {
        this.end_node = end_node;
    }



    // a solution
    List<Point> solution;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public char[][] getMazeArray() {
        return mazeArray;
    }

    public void setMazeArray(char[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public Graph getMazeGraph() {
        return mazeGraph;
    }

    public void setMazeGraph(Graph mazeGraph) {
        this.mazeGraph = mazeGraph;
    }

    public List<Point> getSolution() {
        return solution;
    }

    public void setSolution(List<Point> solution) {
        this.solution = solution;
    }



}
