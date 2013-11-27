package project5;

import java.util.Stack;

/**
 * Implement a graph class that “builds a graph” using vertices and edges,
 * display the adjacency matrix for the graph and implemented Warshall Algorithm
 * (as discussed on pg. 662 of you text). Your graph class should support graphs
 * with at most 10 vertices.
 *
 * @author Jason Travis
 */
class Graph {

    private final int MAX_VERTS = 10;
    private Vertex vertexList[]; // array of vertices
    private int adjMat[][]; // adjacency matrix
    private int nVerts; // current number of vertices

    public Graph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];

        nVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
        {
            for (int k = 0; k < MAX_VERTS; k++) // matrix to 0
            {
                adjMat[j][k] = 0;
            }
        }
    }

    public void addVertex(char lab) // argument is label
    {
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].label);
    }

    /**
     * findCycle prints the Vertex labels of a Hamiltonian Cycle starting with
     * the given Vertex
     * @param i index of the starting Vertex
     */
    public void findCycle(int i) {
        Stack<Vertex> path = new Stack();
        path.push(this.vertexList[i]);
        if (!findCycle_r(path, i, 0)) {
            // No cycle found, clear the stack
            path.pop();
        }
        // Display the cycle
        for (Vertex v : path) {
            System.out.print(v.label + " ");
        }
        System.out.println();
    }

    /**
     * findCycle_r recursively searches for a Hamiltonian Cycle
     * @param path a stack of Vertices in the current path
     * @param start index of the starting Vertex
     * @param vertex index of the current Vertex
     * @return true if a Hamiltonian Cycle was found
     */
    private boolean findCycle_r(Stack path, int start, int vertex) {
        for (int i = 0; i < this.MAX_VERTS; i++) {
            // Find an edge that wasn't visited
            if (this.adjMat[vertex][i] > 0 && !this.vertexList[i].wasVisited) {
                this.vertexList[i].wasVisited = true;
                path.push(this.vertexList[i]);
                // Base case
                if (start == i) {
                    return true;
                }
                // This path results in a cycle
                if (findCycle_r(path, start, i)) {
                    return true;
                }
                // This path doesn't result in a cycle
                this.vertexList[i].wasVisited = false;
                path.pop();
            }
        }
        return false;
    }

    /**
     * adjMatDisplay prints the adjacency matrix
     */
    void adjMatDisplay() {
        for (int y = 0; y < this.MAX_VERTS; y++) {
            for (int x = 0; x < this.MAX_VERTS; x++) {
                System.out.print(this.adjMat[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * The Warshall algorithm modifies the adjacency matrix into a transitive
     * closure. The transitive closure shows whether a path exists between two
     * points regardless of the number of points in between.
     */
    void warshall() {
        for (int y = 0; y < this.MAX_VERTS; y++) {
            for (int x = 0; x < this.MAX_VERTS; x++) {
                if (this.adjMat[y][x] == 1) {
                    for (int z = 0; z < this.MAX_VERTS; z++) {
                        if (this.adjMat[z][y] == 1) {
                            this.adjMat[z][x] = 1;
                        }

                    }
                }
            }
        }
    }

    private class Vertex {

        // label (e.g. ‘A’)
        public char label;
        public boolean wasVisited;

        public Vertex(char lab) {
            label = lab;
            wasVisited = false;
        }
    }

}
