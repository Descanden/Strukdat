package Teori.Tugas4;

public class BFS {
    private int node;
    private int[][] adj;
    private int[] queue;
    private int front;
    private int rear;
    private int size;

    BFS(int v) {
        node = v;
        adj = new int[node][node];
        queue = new int[node];
        front = 0;
        rear = -1;
        size = 0;
    }

    void insertEdge(int v, int w) {
        adj[v][w] = 1;
    }

    void enqueue(int value) {
        if (size == node) {
            return;
        }
        rear = (rear + 1) % node;
        queue[rear] = value;
        size++;
    }

    int dequeue() {
        if (size == 0) {
            return -1;
        }
        int value = queue[front];
        front = (front + 1) % node;
        size--;
        return value;
    }

    boolean isEmpty() {
        return size == 0;
    }

    void BFS(int n) {
        boolean[] visited = new boolean[node];
        visited[n] = true;
        enqueue(n);
        while (!isEmpty()) {
            n = dequeue();
            System.out.print(n + " ");
            for (int i = 0; i < node; i++) {
                if (adj[n][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    enqueue(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        BFS graph = new BFS(12);
        graph.insertEdge(1, 3);
        graph.insertEdge(1, 5);
        graph.insertEdge(0, 5);
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

        System.out.println("Breadth First Traversal for the graph is:");
        graph.BFS(3);
    }
}
