import java.util.*;

public class Question5a {

    // Define parameters for the ACO algorithm
    private int numAnts; // Number of ants
    private int numIterations; // Number of iterations
    private double evaporationRate; // Rate of pheromone evaporation
    private double alpha; // Pheromone attractiveness parameter
    private double beta; // Heuristic information parameter

    private int numCities; // Number of cities
    private double[][] distanceMatrix; // Matrix to store distances between cities
    private double[][] pheromoneMatrix; // Matrix to store pheromone levels between cities

    private Random random; // Random number generator

    // Constructor to initialize parameters and matrices
    public Question5a(int numAnts, int numIterations, double evaporationRate,
                                 double alpha, double beta, double[][] distanceMatrix) {
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.distanceMatrix = distanceMatrix;
        this.numCities = distanceMatrix.length;
        this.random = new Random();

        // Initialize pheromone levels to a small constant value
        this.pheromoneMatrix = new double[numCities][numCities];
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] = 0.01;
            }
        }
    }

    // Method to run the ACO algorithm
    public int[] solveTSP() {
        int[] bestTour = null;
        double bestTourLength = Double.MAX_VALUE;

        // Perform iterations
        for (int iteration = 0; iteration < numIterations; iteration++) {
            // Initialize ant tours
            int[][] antTours = new int[numAnts][numCities + 1];

            // Construct tours for each ant
            for (int ant = 0; ant < numAnts; ant++) {
                antTours[ant] = constructTour();
            }

            // Update pheromone levels
            updatePheromones(antTours);

            // Find the best tour among all ants
            for (int ant = 0; ant < numAnts; ant++) {
                double tourLength = calculateTourLength(antTours[ant]);
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = antTours[ant];
                }
            }

            // Evaporate pheromones
            evaporatePheromones();
        }

        return bestTour;
    }

    // Method to construct a tour for a single ant
    private int[] constructTour() {
        int[] tour = new int[numCities + 1];
        boolean[] visited = new boolean[numCities];
        int currentCity = random.nextInt(numCities);
        tour[0] = currentCity;

        for (int i = 1; i < numCities; i++) {
            visited[currentCity] = true;
            int nextCity = selectNextCity(currentCity, visited);
            tour[i] = nextCity;
            currentCity = nextCity;
        }

        tour[numCities] = tour[0]; // Return to the starting city to complete the tour
        return tour;
    }

    // Method to select the next city for an ant based on pheromone levels and heuristic information
    private int selectNextCity(int currentCity, boolean[] visited) {
        double totalProbability = 0;
        double[] probabilities = new double[numCities];
        
        // Calculate total probability
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[i] = Math.pow(pheromoneMatrix[currentCity][i], alpha) *
                                   Math.pow(1.0 / distanceMatrix[currentCity][i], beta);
                totalProbability += probabilities[i];
            }
        }

        // Choose the next city based on probability
        double rand = random.nextDouble() * totalProbability;
        double sum = 0;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                sum += probabilities[i];
                if (rand <= sum) {
                    return i;
                }
            }
        }
        return -1; // Should never reach here
    }

    // Method to update pheromone levels based on ant tours
    private void updatePheromones(int[][] antTours) {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] *= (1.0 - evaporationRate);
            }
        }

        for (int ant = 0; ant < numAnts; ant++) {
            double pheromoneDelta = 1.0 / calculateTourLength(antTours[ant]);
            for (int i = 0; i < numCities; i++) {
                int from = antTours[ant][i];
                int to = antTours[ant][i + 1];
                pheromoneMatrix[from][to] += pheromoneDelta;
                pheromoneMatrix[to][from] += pheromoneDelta;
            }
        }
    }

    // Method to evaporate pheromones
    private void evaporatePheromones() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    // Method to calculate the length of a tour
    private double calculateTourLength(int[] tour) {
        double length = 0;
        for (int i = 0; i < numCities; i++) {
            length += distanceMatrix[tour[i]][tour[i + 1]];
        }
        return length;
    }

    // Main method to test the Ant Colony Optimization algorithm
    public static void main(String[] args) {
        // Example distance matrix (replace with your own data)
        double[][] distanceMatrix = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        // Initialize ACO parameters
        int numAnts = 10;
        int numIterations = 100;
        double evaporationRate = 0.5;
        double alpha = 1.0;
        double beta = 2.0;

        // Create ACO instance
        Question5a aco = new Question5a(numAnts, numIterations,
                                                               evaporationRate, alpha, beta,
                                                               distanceMatrix);

        // Solve TSP using ACO
        int[] bestTour = aco.solveTSP();

        // Print the best tour
        System.out.println("Best tour found:");
        for (int city : bestTour) {
            System.out.print(city + " ");
        }
    }
}
