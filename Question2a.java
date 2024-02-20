public class Question2a {

    public static int minMoves(int[] machines) {
        int totalDresses = 0;
        int totalMachines = machines.length;

        // Calculate the total number of dresses
        for (int dresses : machines) {
            totalDresses += dresses;
        }

        // If total dresses cannot be evenly distributed, return -1
        if (totalDresses % totalMachines != 0) {
            return -1;
        }

        int targetDresses = totalDresses / totalMachines;
        int moves = 0;
        int cumulativeSum = 0;

        // Iterate through the machines and calculate the moves needed
        for (int dresses : machines) {
            // Calculate the current cumulative sum
            cumulativeSum += dresses - targetDresses;
            // Calculate the moves needed based on the current cumulative sum
            moves = Math.max(moves, Math.max(Math.abs(cumulativeSum), dresses - targetDresses));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] machines = {1, 0, 5};
        int minMoves = minMoves(machines);
        System.out.println("Minimum moves required: " + minMoves); // Output should be 2
    }
}
