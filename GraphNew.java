import java.util.ArrayList;
class pair<T1, T2>{
    T1 a;
    T2 b;
    public pair(T1 de, T2 wgh) {
        this.a = de;
        this.b = wgh;
    }
    T1 getKey(){
        return this.a;
    }
    T2 getValue(){
        return this.b;
    }
    void setValue(T1 val1, T2 val2){
        this.a = val1;
        this.b = val2;
    }
}
class makeGraph {
    ArrayList<ArrayList<pair>> arr = new ArrayList<>();
    int vertices;

    makeGraph(Integer vertices) {
        for (Integer i = 0; i <= 5; i++) {
            this.arr.add(new ArrayList<pair>());
            this.vertices = vertices;
        }
    }
    /** Following graph can work for graph without cycle from source to destination */
    void makeEdges(Character src, Character des, int wgh) {
        Integer sr = src - 'A';
        Integer de = des - 'A';
        this.arr.get(sr).add(new pair(de, wgh));
        /** Remove below line for DAG */
        this.arr.get(de).add(new pair(sr, wgh));
    }

    /** Prints Graph */
    void print() {
        for (int i = 0; i < this.vertices; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < this.arr.get(i).size(); j++) {
                int a = (Integer) this.arr.get(i).get(j).getKey();
                int b = (Integer) this.arr.get(i).get(j).getValue();
                System.out.print("{" + a + " " + b + "} ");
            }
            System.out.println();
        }
    }
}

public class GraphNew {
    public static void makeConnection(makeGraph g){
        g.makeEdges('A', 'B', 12);
        g.makeEdges('A', 'C', 13);
        g.makeEdges('A', 'E', 8);
        g.makeEdges('A', 'D', 11);
        g.makeEdges('D', 'E', 7);
        g.makeEdges('E', 'C', 4);
        g.makeEdges('B', 'C', 3);
    }
    public static double calculateAverageDistanceBetweenTwoPoints(String X, String Y, makeGraph g){
        Integer src = X.charAt(0)- 'A';
        Integer des = Y.charAt(0) - 'A';
        ArrayList<ArrayList<pair>> totalPath = new ArrayList<ArrayList<pair>>();
        ArrayList<pair> path = new ArrayList<pair>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        for(int i = 0; i < g.vertices; i++)visited.add(0);

        dfs(g, totalPath, path, src, des, 0 , visited);

        int totalPathCount = totalPath.size(), totalDistance = 0;
        for(ArrayList<pair> it: totalPath){
            for(pair it1 : it) {
                totalDistance += (Integer) it1.getValue();
            }
        }
        return (double) totalDistance / totalPathCount;
    }
    private static void dfs(makeGraph g, ArrayList<ArrayList<pair>> totalPath, ArrayList<pair> path,
                            Integer src, Integer des, Integer wgh, ArrayList<Integer> visited) {
        visited.set(src, 1);
        path.add(new pair(src, wgh));
        if(src.equals(des)){
            totalPath.add(new ArrayList<pair>(path));
        }

        for(pair p : g.arr.get(src)){
            if(visited.get((Integer) p.a).equals(0)) {
                int a = (Integer) p.a;
                int b = (Integer) p.b;
                dfs(g, totalPath, path, a, des, b, visited);
            }
        }
        visited.set(src, 0);
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        Integer vertices = 5;
        makeGraph g = new makeGraph(vertices);
        makeConnection(g);
        Double ans = calculateAverageDistanceBetweenTwoPoints("A",  "C", g);
        System.out.print(ans);
        System.out.println();
    }

}