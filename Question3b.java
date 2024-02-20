import java.util.*;

// Class to represent an edge in the graph
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Comparing edges based on weights
    @Override
    public int compareTo(Edge otherEdge) {
        return this.weight - otherEdge.weight;
    }
}

// Class to implement Disjoint Set data structure
class DisjointSet {
    Map<Integer, Integer> parent;
    Map<Integer, Integer> rank;

    // Constructor
    public DisjointSet(int[] vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (int vertex : vertices) {
            parent.put(vertex, vertex); // Initialize each vertex as its own parent
            rank.put(vertex, 0); // Initialize rank of each vertex as 0
        }
    }

    // Find operation to determine the set a vertex belongs to
    public int find(int vertex) {
        if (parent.get(vertex) != vertex) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    // Union operation to merge two sets
    public void union(int vertex1, int vertex2) {
        int root1 = find(vertex1);
        int root2 = find(vertex2);

        if (root1 != root2) {
            if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}

// Priority Queue implementation using Min Heap
class PriorityQueueMinHeap<T extends Comparable<T>> {
    private List<T> heap;

    // Constructor
    public PriorityQueueMinHeap() {
        heap = new ArrayList<>();
    }

    // Add an element to the heap
    public void add(T element) {
        heap.add(element);
        int index = heap.size() - 1;
        heapifyUp(index);
    }

    // Remove and return the minimum element from the heap
    public T poll() {
        if (isEmpty()) throw new NoSuchElementException();
        T minElement = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heapifyDown(0);
        }
        return minElement;
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Restore heap property starting from the given index (upward)
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Restore heap property starting from the given index (downward)
    private void heapifyDown(int index) {
        int smallest = index;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChildIndex;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

// Main class for Kruskal's Algorithm
public class Question3b {
    // Kruskal's algorithm to find Minimum Spanning Tree
    public static List<Edge> kruskalMST(List<Edge> edges, int[] vertices) {
        List<Edge> mst = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(vertices);
        PriorityQueueMinHeap<Edge> minHeap = new PriorityQueueMinHeap<>();

        // Add all edges to the priority queue
        for (Edge edge : edges) {
            minHeap.add(edge);
        }

        // Process edges until the Minimum Spanning Tree is found
        while (!minHeap.isEmpty() && mst.size() < vertices.length - 1) {
            Edge edge = minHeap.poll();
            int srcRoot = disjointSet.find(edge.src);
            int destRoot = disjointSet.find(edge.dest);
            // Check if adding the edge forms a cycle or not
            if (srcRoot != destRoot) {
                mst.add(edge); // Add edge to the MST
                disjointSet.union(srcRoot, destRoot); // Union the sets
            }
        }

        return mst;
    }

    // Main method to test the Kruskal's Algorithm
    public static void main(String[] args) {
        int[] vertices = {0, 1, 2, 3, 4, 5};
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 5, 2));
        edges.add(new Edge(1, 5, 5));
        edges.add(new Edge(1, 2, 6));
        edges.add(new Edge(5, 2, 1));
        edges.add(new Edge(5, 4, 3));
        edges.add(new Edge(2, 4, 5));
        edges.add(new Edge(2, 3, 3));
        edges.add(new Edge(4, 3, 2));

        List<Edge> mst = kruskalMST(edges, vertices);

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + ": " + edge.weight);
        }
    }
}
