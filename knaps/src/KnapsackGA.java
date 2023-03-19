import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnapsackGA {

    private int populationSize;
    private int generations;
    private double mutationRate;
    private int[] weights;
    private int[] values;
    private int capacity;
    private int itemNum;
    private Random rand = new Random();


    public KnapsackGA(int populationSize, int generations, double mutationRate, int[] weights, int[] values, int capacity) {
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
        this.weights = weights;
        this.values = values;
        this.capacity = capacity;
        this.itemNum = weights.length;}

    // Generates a bit string of length (total number of items) with either 0 or 1 .
    // 0 indicating it is absent while 1 states that the item is chosen
    public int[] generateRandomChromosome() {
        int[] chromosome = new int[itemNum];
        for (int i = 0; i < itemNum; i++) {
            chromosome[i] = rand.nextInt(2);
        }
        return chromosome;
    }

    // Generates our population of size 10
    public List<int[]> generatePopulation() {
        List<int[]> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomChromosome());
        }
        return population;  }

    // This method returns the calculated fitness
    public int calculateFitness(int[] chromosome) {
        int weight = 0;
        int value = 0;
        for (int i = 0; i < itemNum; i++) {
            if (chromosome[i] == 1) {
                weight += weights[i];
                value += values[i];
            }
        }
        // Set fitness to 0 of it exceeds the capacity
        if (weight > capacity) {
            value = 0;
        }
        return value;}

    // As for the selection we pick the chromosome with the highest fitness, and then select
    // a second chromosome at random from the remaining population.
    // The max fitness ensures optimality while the random brings in the diversity dimension
    public int[][] selection(List<int[]> population) {
        int[][] selectedChromosomes = new int[2][itemNum];
        int[] maxChromosome = population.get(0);
        int maxFitness = calculateFitness(maxChromosome);
        for (int i = 1; i < populationSize; i++) {
            int[] chromosome = population.get(i);
            int fitness = calculateFitness(chromosome);
            if (fitness > maxFitness) {
                maxChromosome = chromosome;
                maxFitness = fitness;
            }
        }
        selectedChromosomes[0] = maxChromosome;
        int index = rand.nextInt(populationSize);
        selectedChromosomes[1] = population.get(index);
        return selectedChromosomes;}

    public int[] crossover(int[] chromosome1, int[] chromosome2) {
        int[] newChromosome = new int[itemNum];
        int midpoint = rand.nextInt(itemNum);
        for (int i = 0; i < itemNum; i++) {
            if (i < midpoint) {
                newChromosome[i] = chromosome1[i];
            } else {
                newChromosome[i] = chromosome2[i];
            }
        }
        return newChromosome;   }

    // Mutation happens for every chromosome only if the random generated number is less than the mutation rate
    public int[] mutation(int[] chromosome) {
        int[] newChromosome = chromosome.clone();
        for (int i = 0; i < itemNum; i++) {
            if (rand.nextDouble() < mutationRate) {
                newChromosome[i] = 1 - newChromosome[i];
            }
        }
        return newChromosome;  }

    // The evolve method performs the selection, crossover and mutation
    // embracing the Darwin survival of the fittest evolution cycle
    public int[] evolve(List<int[]> population) {
        int[][] selectedChromosomes = selection(population);
        int[] chromosome1 = selectedChromosomes[0];
        int[] chromosome2 = selectedChromosomes[1];
        int[] newChromosome = crossover(chromosome1, chromosome2);
        newChromosome = mutation(newChromosome);
        return newChromosome;}

    public int[] solve() {
        List<int[]> population = generatePopulation();
        int[] bestChromosome = population.get(0);
        int bestFitness = calculateFitness(bestChromosome);
        for (int i = 0; i < generations; i++) {
            List<int[]> newPopulation = new ArrayList<>();
            for (int j = 0; j < populationSize; j++) {
                int[] newChromosome = evolve(population);
                newPopulation.add(newChromosome);
                int newFitness = calculateFitness(newChromosome);
                if (newFitness > bestFitness) {
                    bestChromosome = newChromosome;
                    bestFitness = newFitness;
                }
            }
            population = newPopulation;
        }
        return bestChromosome;
    }
}