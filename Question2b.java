import java.util.ArrayList;
import java.util.List;

public class Question2b {

    // Disjoint Set data structure for managing groups of individuals
    static class DisjointSet {
        int[] parent;

        // Constructor: Initialize the disjoint set with each individual as its own parent
        public DisjointSet(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        // Find operation to determine the group (parent) of a given individual
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression to optimize the find operation
            }
            return parent[x];
        }

        // Union operation to merge two groups
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootX] = rootY; // Set the root of one group as the parent of the other
            }
        }
    }

    // Method to find individuals who eventually know the secret
    public static List<Integer> findSecretRecipients(int n, int[][] intervals, int firstPerson) {
        DisjointSet disjointSet = new DisjointSet(n); // Initialize disjoint set for all individuals

        // Union all individuals within the intervals
        for (int[] interval : intervals) {
            for (int i = interval[0]; i <= interval[1]; i++) {
                disjointSet.union(interval[0], i); // Union the individuals within the interval
            }
        }

        List<Integer> recipients = new ArrayList<>(); // List to store individuals who know the secret
        // Iterate through all individuals to find those in the same group as the first person
        for (int i = 0; i < n; i++) {
            if (disjointSet.find(i) == disjointSet.find(firstPerson)) {
                recipients.add(i); // Add individuals in the same group as the first person
            }
        }

        return recipients; // Return the list of individuals who eventually know the secret
    }

    // Main method to test the findSecretRecipients function
    public static void main(String[] args) {
        int n = 5; // Total number of individuals
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}}; // Intervals within which individuals are connected
        int firstPerson = 0; // The first person who knows the secret

        List<Integer> recipients = findSecretRecipients(n, intervals, firstPerson); // Find recipients
        System.out.println("Individuals who eventually know the secret: " + recipients); // Print recipients
    }
}
