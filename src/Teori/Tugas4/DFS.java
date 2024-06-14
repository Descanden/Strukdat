package Teori.Tugas4;

public class DFS {
    private int[][] adj;
    private boolean[] visited;
    private int[] stack;
    private int top;

    DFS(int V) {
        adj = new int[V][V];
        visited = new boolean[V];
        stack = new int[V];
        top = -1;
    }

    void insertEdge(int src, int dest) {
        adj[src][dest] = 1;
    }

    void push(int vertex) {
        stack[++top] = vertex;
    }

    int pop() {
        return stack[top--];
    }

    boolean isEmpty() {
        return top == -1;
    }

    void DFS(int vertex) {
        push(vertex);
        while (!isEmpty()) {
            vertex = pop();
            if (!visited[vertex]) {
                System.out.print(vertex + " ");
                visited[vertex] = true;
            }
            for (int i = adj[vertex].length - 1; i >= 0; i--) {
                if (adj[vertex][i] == 1 && !visited[i]) {
                    push(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        DFS graph = new DFS(12);
        graph.insertEdge(1, 3);
        graph.insertEdge(1, 5);
        graph.insertEdge(2, 5);
        graph.insertEdge(2, 6);
        graph.insertEdge(3, 4);
        graph.insertEdge(3, 5);
        graph.insertEdge(3, 7);
        graph.insertEdge(4, 10);
        graph.insertEdge(5, 2);
        graph.insertEdge(5, 6);
        graph.insertEdge(5, 9);
        graph.insertEdge(6, 1);
        graph.insertEdge(7, 8);
        graph.insertEdge(7, 11);
        graph.insertEdge(8, 4);
        graph.insertEdge(11, 1);

        System.out.println("Depth First Traversal for the graph is:");
        graph.DFS(3);
    }
}
