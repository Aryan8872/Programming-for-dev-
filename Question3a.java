class Score {
    double[] scores;
    int size;

    public Score() {
        scores = new double[1000]; // initial capacity, can be adjusted as needed
        size = 0;
    }

    public void addScore(double score) {
      
        scores[size++] = score;
        bubbleSort(scores, size); // sort the array up to size
    }

    public double getMedianScore() {
        if (size == 0) {
            return 0.0; // no scores available
        }

        if (size % 2 == 0) {
            int mid = size / 2;
            return (scores[mid - 1] + scores[mid]) / 2;
        } else {
            // else, median is the middle score
            return scores[size / 2];
        }
    }

    private void bubbleSort(double[] array, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // swap array[j] and array[j+1]
                    double temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }


}

public class Question3a {
    public static void main(String[] args) {
        Score scoreTracker = new Score();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2);
    }
}

