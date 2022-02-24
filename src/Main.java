public class Main {
    public static void main(String[] args) {
        JavaDFS graph = new JavaDFS(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);
        graph.addEdge(1, 3);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 3);
        graph.addEdge(5, 3);

        System.out.println("Following is the Depth First Traversal");
        graph.DFS(0);
    }
}
