import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Graph {
    private int node;
    private ArrayList<Edge>[] adjacent;
    private HashMap<String, Integer> hashcode;
    private int paths;
    private int resultantWT;
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge( int destination, int weight) {
          //  this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
    public Graph() {
        node = 5;
        paths = 0;
        resultantWT = 0;
        this.hashcode = new HashMap<>();
        alphacode();
        adjacent = new ArrayList[this.node];
        for(int i = 0; i < this.node; i++) {
            adjacent[i] = new ArrayList<>();
        }
    }

    public void addnode(String src, String dest, int wt) {
        int source = hashcode.get(src);
        int destination = hashcode.get(dest);
        Edge edge = new Edge(destination, wt);
        adjacent[source].add(edge);
        Edge edge1 = new Edge(source,wt);
        adjacent[destination].add(edge1);

    }
    private void depthFirstSearch(int src, int dest, boolean[] visit, List<Integer> locPath) {
        if(src == dest) {
            System.out.println("Path " + locPath);
            this.paths = this.paths + 1;
            return;
        }
        visit[src] = true;
        for(Edge i : adjacent[src]) {
            if(!visit[i.destination]) {
                locPath.add(i.destination);
                this.resultantWT = this.resultantWT + i.weight;
                depthFirstSearch(i.destination, dest, visit, locPath);
                int index = locPath.size() - 1;
                locPath.remove(index);
            }
        }
        visit[src] = false;
    }
    private void alphacode() {
        int count = 0;
        for (Character i = 'A'; i <= 'Z'; i++) {
            hashcode.put(Character.toString(i), count);
            count++;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addnode("A", "B", 12);
        graph.addnode("A", "C", 13);
        graph.addnode("A", "D", 11);
        graph.addnode("A", "E", 8);
        graph.addnode("B", "C", 3);
        graph.addnode("D", "E", 7);
        graph.addnode("E", "C", 4);
        System.out.println(graph.calculateAverageDistanceBetweenTwoPoints("A", "C"));
    }
    public int calculateAverageDistanceBetweenTwoPoints(String x, String y) {
        int src = hashcode.get(x);
        int dest = hashcode.get(y);
        boolean[] visit = new boolean[node];
        ArrayList<Integer> list = new ArrayList<>();
        list.add(src);
        depthFirstSearch(src, dest, visit, list);
        return (this.resultantWT / this.paths);
    }
}









