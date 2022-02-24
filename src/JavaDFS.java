import java.util.LinkedList;
import java.util.Stack;

public class JavaDFS {
    int V;                                       //number of nodes

    LinkedList<Integer>[] adj;                      //adjacency list

    JavaDFS(int V)
    {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < adj.length; i++)
            adj[i] = new LinkedList<Integer>();
    }

    void addEdge(int v, int w)
    {
        adj[v].add(w);                              //adding an edge to the adjacency list (edges are bidirectional in this example)
    }

    void DFS(int n)
    {
        boolean nodes[] = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        stack.push(n);                                    //push root node to the stack
        int a = 0;
        while(!stack.empty())
        {
            n = stack.peek();                       //extract the top element of the stack
            stack.pop();                            //remove the top element from the stack
            if(nodes[n] == false)
            {
                System.out.print(n + " ");
                nodes[n] = true;
            }
            for (int i = 0; i < adj[n].size(); i++)  //iterate through the linked list and then propagate to the next few nodes
            {
                a = adj[n].get(i);
                if (!nodes[a])                    //only push those nodes to the stack which aren't in it already
                {
                    stack.push(a);                          //push the top element to the stack
                }
            }
        }
    }
}
