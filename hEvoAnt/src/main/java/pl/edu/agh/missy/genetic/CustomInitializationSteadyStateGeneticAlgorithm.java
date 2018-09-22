package pl.edu.agh.missy.genetic;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomInitializationSteadyStateGeneticAlgorithm extends SteadyStateGeneticAlgorithm<PermutationSolution<Integer>> {

    private final BSFResultSaver resultSaver;
    private final GenotypeProvider initialGenotypesProvider;

    CustomInitializationSteadyStateGeneticAlgorithm(Problem<PermutationSolution<Integer>> problem,
                                                           BSFResultSaver resultSaver,
                                                           int maxEvaluations,
                                                           int populationSize,
                                                           CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                                           MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                                           SelectionOperator<List<PermutationSolution<Integer>>,
                                                                   PermutationSolution<Integer>> selectionOperator) {
        this(null, problem, resultSaver, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator);
    }

    CustomInitializationSteadyStateGeneticAlgorithm(GenotypeProvider initialGenotypesProvider,
                                                           Problem<PermutationSolution<Integer>> problem,
                                                           BSFResultSaver resultSaver,
                                                           int maxEvaluations,
                                                           int populationSize,
                                                           CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                                           MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                                           SelectionOperator<List<PermutationSolution<Integer>>,
                                                                   PermutationSolution<Integer>> selectionOperator) {

        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator);
        this.initialGenotypesProvider = initialGenotypesProvider;
        this.resultSaver = resultSaver;
    }

    @Override
    protected List<PermutationSolution<Integer>> createInitialPopulation() {
        return Optional.ofNullable(initialGenotypesProvider).map(populationSupplier -> {
            List<PermutationSolution<Integer>> population = new ArrayList<>(getMaxPopulationSize());
            for (int i = 0; i < getMaxPopulationSize(); i++) {
                PermutationSolution<Integer> newIndividual = populationSupplier.nextGenotype();
                population.add(newIndividual);
            }
            return population;
        }).orElseGet(() -> super.createInitialPopulation());
    }

    @Override
    protected List<PermutationSolution<Integer>> selection(List<PermutationSolution<Integer>> population){
        resultSaver.recordCheckpoint(getResult().getObjective(0), "evo");
        return super.selection(population);
    }
}
