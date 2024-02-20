import java.util.Arrays;

public class Question1b {

    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        int n = engines.length;
        Arrays.sort(engines); // Sort engines in non-ascending order
        int totalTime = 0;
        int engineers = 1; // Start with one engineer

        for (int i = n - 1; i >= 0; i--) {
            // If there's only one engineer and more than one engine, split the engineer
            if (engineers == 1 && i < n - 1) {
                int splitTime = splitCost;
                totalTime += splitTime;
                engineers *= 2; // Double the number of engineers
            } else {
                totalTime += engines[i]; // Assign the engineer(s) to build the engines
            }
        }

        return totalTime;
    }

    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;

        int minTime = minTimeToBuildEngines(engines, splitCost);
        System.out.println("Minimum time needed to build all engines: " + minTime);
    }
}
