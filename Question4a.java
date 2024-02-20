import java.util.*;

public class Question4a {
    public int collectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] start = new int[2]; // Store the starting position
        int totalKeys = 0; // Count the total number of keys in the maze

        // Find the starting position and count the total number of keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (grid[i][j] >= 'a' && grid[i][j] <= 'z') {
                    totalKeys++;
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>(); // Queue for BFS traversal
        Set<String> visited = new HashSet<>(); // Set to keep track of visited cells
        queue.offer(new int[]{start[0], start[1], 0}); // {x, y, keys}
        visited.add(start[0] + "," + start[1] + ",0");

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int steps = 0; // Variable to count the number of steps

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int x = curr[0];
                int y = curr[1];
                int keys = curr[2];

                // If all keys are collected, return the number of steps
                if (keys == (1 << totalKeys) - 1) {
                    return steps;
                }

                // Explore all four directions
                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    // Check if the new position is within the maze boundaries and is not a wall
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                        char cell = grid[newX][newY];

                        // If the cell is an empty path or a key
                        if (cell == 'P' || (cell >= 'a' && cell <= 'z')) {
                            int newKeys = keys;
                            // If the cell contains a key, update the keys bitmask
                            if (cell >= 'a' && cell <= 'z') {
                                newKeys |= 1 << (cell - 'a');
                            }
                            // If the new position hasn't been visited, add it to the queue
                            if (!visited.contains(newX + "," + newY + "," + newKeys)) {
                                queue.offer(new int[]{newX, newY, newKeys});
                                visited.add(newX + "," + newY + "," + newKeys);
                            }
                        } 
                        // If the cell is a locked door
                        else if (cell >= 'A' && cell <= 'Z' && ((keys >> (cell - 'A')) & 1) == 1) {
                            // Check if we have the corresponding key to unlock the door
                            if (!visited.contains(newX + "," + newY + "," + keys)) {
                                queue.offer(new int[]{newX, newY, keys});
                                visited.add(newX + "," + newY + "," + keys);
                            }
                        }
                    }
                }
            }
            steps++; // Increment steps after exploring each level
        }

        return -1; // Cannot collect all keys
    }

    public static void main(String[] args) {
        Question4a solution = new Question4a();
        char[][] grid = {
            {'S', 'P', 'q', 'P', 'P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r', 'P', 'Q', 'P', 'R'}
        };
        int result = solution.collectKeys(grid);
        System.out.println("Minimum number of moves required to collect all keys: " + result);
    }
}
