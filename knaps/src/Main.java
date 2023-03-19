// Alexander Safi
// Evolutionary Algorithm
// Basic implementation of Genetic Algorithm on the Knapsack problem
public class Main {
    public static void main(String[]args)
    {
        int[] weights = {2, 3, 4, 5, 8};
        int[] values = {3, 4, 5, 6, 9};
        int capacity = 10;
        int populationSize = 10;
        int generations = 100;
        double mutationRate = 0.1;
        KnapsackGA knapsackGA = new KnapsackGA(populationSize, generations, mutationRate, weights, values, capacity);
        int[] solution = knapsackGA.solve();
        System.out.print("Solution: ");
        for (int i = 0; i < solution.length; i++) {
            System.out.print(solution[i] + " ");
        }
        System.out.println("\nTotal value: " + knapsackGA.calculateFitness(solution));
    }
    }




