import java.util.LinkedList;
import java.util.Queue;

public class Question4a {
    // Function to find the minimum number of moves to collect all keys
    public static int findMinimumMovesToCollectAllKeys(String[] maze) {
        int rows = maze.length;
        int cols = maze[0].length();
        int targetKeys = 0;
        int startX = 0, startY = 0;

        // Find the starting point and target keys
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = maze[i].charAt(j);
                if (cell == 'S') {
                    startX = i;
                    startY = j;
                } else if (cell == 'E') {
                    targetKeys |= (1 << ('f' - 'a'));
                } else if (cell >= 'a' && cell <= 'f') {
                    targetKeys |= (1 << (cell - 'a'));
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][1 << 6];
        queue.offer(new int[] { startX, startY, 0, 0 });

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        // Perform BFS to explore the maze
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int keys = current[2];
            int steps = current[3];
            
            // If all keys are collected, return the number of steps
            if (keys == targetKeys) {
                return steps;
            }
            
            // Explore neighboring cells
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && maze[newX].charAt(newY) != 'W') {
                    char cell = maze[newX].charAt(newY);
                    if (cell == 'E' || cell == 'P' || (cell >= 'a' && cell <= 'f')
                            || (cell >= 'A' && cell <= 'F' && (keys & (1 << (cell - 'A'))) != 0)) {
                        int newKeys = keys;
                        if (cell >= 'a' && cell <= 'f') {
                            newKeys |= (1 << (cell - 'a'));
                        }

                        if (!visited[newX][newY][newKeys]) {
                            visited[newX][newY][newKeys] = true;
                            queue.offer(new int[] { newX, newY, newKeys, steps + 1 });
                        }
                    }
                }
            }
        }

        return -1; // If all keys cannot be collected
    }

    // Main method for testing
    public static void main(String[] args) {
        String[] maze = { "SPaPP", "WWWPW", "bPAPB" };
        int result = findMinimumMovesToCollectAllKeys(maze);
        System.out.println("Minimum number of moves: " + result);
    }
}
