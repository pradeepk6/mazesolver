package mycompany;


import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Algo implements SolveMaze {

    public List<Point> solve(Maze maze) {
        Graph g = maze.getMazeGraph();
        int start_node = maze.getStart_node();
        int end_node = maze.getEnd_node();
        List<Point> path = new ArrayList<>();
        boolean visited[] = new boolean[g.num_nodes];
        for (int i = 0; i < g.num_nodes; i++)
            visited[i] = false;
        visited[start_node] = true;
        Stack<Integer> s = new Stack<Integer>();
        s.push(start_node);
        int pom,pom1;
        while (!s.isEmpty() && s.peek()!=end_node) {
            pom = s.peek();
            pom1 = pom;
            for (int i = 0; i < g.num_nodes; i++) {
                if(g.adjacent(pom,i)==1){
                    pom1 = i;
                    if(!visited[i])
                        break;
                }
            }
            if(!visited[pom1]){
                visited[pom1] = true;
                s.push(pom1);
            }
            else
                s.pop();
        }

        Stack<Integer> os = new Stack<Integer>();
        while(!s.isEmpty())
            os.push(s.pop());
        while(!os.isEmpty()) {
            //String t=null;
            Iterator<Map.Entry<Point,Integer>> it=(maze.getNodeMap().entrySet()).iterator();
            int i=os.pop();
            while(it.hasNext()) {
                Map.Entry<Point, Integer> entry=it.next();
                if (entry.getValue()==i) {
                    path.add(entry.getKey());
                    //System.out.println(entry.getKey().toString() + "  " + entry.getValue() );
                    break;
                }
            }
        }
        return path;
    }
}
