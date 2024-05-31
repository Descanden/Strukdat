package Modul6;

import java.util.*;

public class DFS {
    private LinkedList<Integer> adj[];
    private boolean visited[];

    DFS(int V) {
        adj = new LinkedList[V];
        visited = new boolean[V];

        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<Integer>();
    }

    void insertEdge(int src, int dest) {
        adj[src].add(dest);
    }

    void DFS(int vertex) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        Iterator<Integer> it = adj[vertex].listIterator();
        while (it.hasNext()) {
            int n = it.next();
            if (!visited[n]) {
                DFS(n);
            }
        }
    }

    public static void main(String[] args) {
        DFS graph = new DFS(6);
        graph.insertEdge(0, 1);
        graph.insertEdge(0, 4);
        graph.insertEdge(0, 5);
        graph.insertEdge(1, 0);
        graph.insertEdge(1, 2);
        graph.insertEdge(1, 5);
        graph.insertEdge(2, 1);
        graph.insertEdge(2, 3);
        graph.insertEdge(2, 4);
        graph.insertEdge(3, 2);
        graph.insertEdge(4, 0);
        graph.insertEdge(4, 2);
        graph.insertEdge(4, 5);
        graph.insertEdge(5, 0);
        graph.insertEdge(5, 1);
        graph.insertEdge(5, 4);

        System.out.println("Depth First Traversal for the graph is:");
        graph.DFS(3);
    }
}