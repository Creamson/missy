package pl.edu.agh.missy.genetic;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomInitializationGenerationalGeneticAlgorithm extends GenerationalGeneticAlgorithm<PermutationSolution<Integer>> {

    private GenotypeProvider initialGenotypeProvider;

    public CustomInitializationGenerationalGeneticAlgorithm(GenotypeProvider initialGenotypeProvider,
                                                            Problem<PermutationSolution<Integer>> problem,
                                                            int maxEvaluations,
                                                            int populationSize,
                                                            CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                                            MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                                            SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selectionOperator,
                                                            SolutionListEvaluator<PermutationSolution<Integer>> evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
        this.initialGenotypeProvider = initialGenotypeProvider;
    }

    public CustomInitializationGenerationalGeneticAlgorithm(Problem<PermutationSolution<Integer>> problem,
                                                            int maxEvaluations,
                                                            int populationSize,
                                                            CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                                            MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                                            SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selectionOperator,
                                                            SolutionListEvaluator<PermutationSolution<Integer>> evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, evaluator);
    }

    @Override
    protected List<PermutationSolution<Integer>> createInitialPopulation() {
        return Optional.ofNullable(initialGenotypeProvider).map(populationSupplier -> {
            List<PermutationSolution<Integer>> population = new ArrayList<>(getMaxPopulationSize());
            for (int i = 0; i < getMaxPopulationSize(); i++) {
                PermutationSolution<Integer> newIndividual = populationSupplier.nextGenotype();
                population.add(newIndividual);
            }
            return population;
        }).orElseGet(() -> super.createInitialPopulation());
    }

}
