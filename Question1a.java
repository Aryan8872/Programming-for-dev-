public class Question1a {

    public static int[][] createDP(int[][] costs) {
        int rows = costs.length;   //taking lenght of the provided array input so that rows can be created (length of array = rows of the matrix)
        int columns = costs[0].length;  //taking the length of the first element of the array so that the columns can be created (length of individual arrray = columns of matrix)
    
        int[][] dp = new int[rows][columns];
    
        // Initialize the first row of dp with costs of decorating the first venue
        for (int j = 0; j < columns; j++) {
            dp[0][j] = costs[0][j];
        }

        return dp;
    }

    

    public static int findMinCost(int[][] costsArray) {
        if (costsArray == null || costsArray.length == 0 || costsArray[0].length == 0)
            return 0;

        int[][] dp = createDP(costsArray);   //creating the 2D array representing costs

        int rows = dp.length;   //taking lenght of the provided array input so that rows can be created (length of array = rows of the matrix)
        int columns = dp[0].length;  //taking the length of the first element of the array so that the columns can be created (length of individual arrray = columns of matrix)

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int minCost = Integer.MAX_VALUE;
                for (int l = 0; l < columns; l++) {
                    if (j != l) {
                        minCost = Math.min(minCost, dp[i - 1][l]);
                    }
                }
                dp[i][j] = costsArray[i][j] + minCost;
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < columns; j++) {
            minCost = Math.min(minCost, dp[rows - 1][j]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println("Minimum cost to decorate all venues: " + findMinCost(costs)); // Output: 7
    }
}




