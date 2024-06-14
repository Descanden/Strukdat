package Modul6.Tugas2;

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

        // graph.insertEdge(1, 2);
        // graph.insertEdge(1, 4);
        // graph.insertEdge(1, 6);
        // graph.insertEdge(2, 5);
        // graph.insertEdge(3, 9);
        // graph.insertEdge(4, 6);
        // graph.insertEdge(4, 9);
        // graph.insertEdge(5, 7); 
        // graph.insertEdge(5, 10);
        // graph.insertEdge(6, 5); 
        // graph.insertEdge(7, 11);
        // graph.insertEdge(8, 7);
        // graph.insertEdge(9, 3);
        // graph.insertEdge(9, 6); 
        // graph.insertEdge(11, 8);
        
        System.out.println("Depth First Traversal for the graph is:");
        graph.DFS(3);
    }
}
