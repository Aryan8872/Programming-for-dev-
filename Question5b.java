import java.util.*;

public class Question5b {

    public static Map<Integer, Integer> findImpactedDevices(int[][] connections, int targetDevice) {
        // Build the graph representation
        Map<Integer, List<Integer>> graph = buildGraph(connections);
        
        // Perform DFS traversal starting from the target device to identify directly impacted devices
        Map<Integer, Integer> directlyImpactedDevices = dfs(graph, targetDevice);
        
        return directlyImpactedDevices;
    }

    private static Map<Integer, List<Integer>> buildGraph(int[][] connections) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        
        // Initialize the graph with empty lists for each device
        for (int i = 0; i < connections.length; i++) {
            graph.put(i, new ArrayList<>());
        }
        
        // Populate the graph with connections
        for (int[] connection : connections) {
            int device1 = connection[0];
            int device2 = connection[1];
            // Add edges for both directions since it's an undirected graph
            graph.get(device1).add(device2);
            graph.get(device2).add(device1);
        }
        
        return graph;
    }

    private static Map<Integer, Integer> dfs(Map<Integer, List<Integer>> graph, int currentDevice) {
        Map<Integer, Integer> directlyImpactedDevices = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        stack.push(currentDevice);
        visited.add(currentDevice);
        parentMap.put(currentDevice, -1); // Set parent of targetDevice as -1

        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    stack.push(neighbor);
                    parentMap.put(neighbor, node); // Set parent of neighbor
                    if (graph.get(neighbor).size() == 1 && graph.get(neighbor).get(0) == node) {
                        directlyImpactedDevices.put(neighbor, node);
                    }
                }
            }
        }

        return directlyImpactedDevices;
    }

    public static void main(String[] args) {
        int[][] connections = {{0, 2}, {0, 1}, {2, 4}, {1, 6}, {1, 3}, {4, 6}, {3, 6}, {4, 5}, {5, 7}};
        int targetDevice = 4;
        
        Map<Integer, Integer> directlyImpactedDevices = findImpactedDevices(connections, targetDevice);
        
        System.out.println("Directly Impacted Device List for Target Device " + targetDevice + ": " + directlyImpactedDevices);
    }
}
