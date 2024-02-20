public class Question1b {
    // Method to calculate the minimum time required to construct engines
    public static int findMinimumConstructionTime(int[] constructionDurations, int splitExpense) {
        int totalDuration = 0;
        // Iterate through each engine construction duration
        for (int duration : constructionDurations) {
            // Check if splitting the construction time reduces the total duration
            if (splitExpense + duration / 2 < duration) {
                // If splitting reduces time, add split expense to total duration
                totalDuration += splitExpense;
            } else {
                // Otherwise, add full construction duration to total duration
                totalDuration += duration;
            }
        }
        return totalDuration;
    }

    public static void main(String[] args) {
        int[] constructionDurations = {1, 2, 3}; // Array representing construction durations of engines
        int splitExpense = 1; // Cost to split construction time

        // Calculate and print the minimum time to construct engines
        System.out.println("Minimum time to construct engines: " + findMinimumConstructionTime(constructionDurations, splitExpense));
    }
}
