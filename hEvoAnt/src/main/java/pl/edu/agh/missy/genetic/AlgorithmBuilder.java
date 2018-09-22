package pl.edu.agh.missy.genetic;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.SteadyStateGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.util.List;
import java.util.Optional;

public class AlgorithmBuilder {

    private Problem<PermutationSolution<Integer>> problem;
    private int maxEvaluations;
    private int populationSize;
    private CrossoverOperator<PermutationSolution<Integer>> crossoverOperator;
    private MutationOperator<PermutationSolution<Integer>> mutationOperator;
    private SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selectionOperator;
    private SolutionListEvaluator<PermutationSolution<Integer>> evaluator;
    private GenotypeProvider genotypeProvider;

    private SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> defaultSelectionOperator = new BinaryTournamentSelection<>();
    private BSFResultSaver resultSaver;

    /**
     * Builder constructor
     */
    public AlgorithmBuilder(Problem<PermutationSolution<Integer>> problem,
                            CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                            MutationOperator<PermutationSolution<Integer>> mutationOperator) {
        this.problem = problem;
        maxEvaluations = 25000;
        populationSize = 100;
        this.mutationOperator = mutationOperator;
        this.crossoverOperator = crossoverOperator;
        this.selectionOperator = defaultSelectionOperator;

        evaluator = new SequentialSolutionListEvaluator<>();
    }

    public AlgorithmBuilder setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public AlgorithmBuilder setPopulationSize(int populationSize) {
        this.populationSize = populationSize;

        return this;
    }

    public AlgorithmBuilder setSelectionOperator(SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selectionOperator) {
        this.selectionOperator = selectionOperator;

        return this;
    }

    public AlgorithmBuilder setSolutionListEvaluator(SolutionListEvaluator<PermutationSolution<Integer>> evaluator) {
        this.evaluator = evaluator;

        return this;
    }

    public void setGenotypeProvider(GenotypeProvider genotypeProvider) {
        this.genotypeProvider = genotypeProvider;
    }

    public SteadyStateGeneticAlgorithm<PermutationSolution<Integer>> buildAsSteadyState() {
        return Optional.ofNullable(genotypeProvider)
                .map(provider ->
                        new CustomInitializationSteadyStateGeneticAlgorithm(
                                provider,
                                problem,
                                resultSaver,
                                maxEvaluations,
                                populationSize,
                                crossoverOperator,
                                mutationOperator,
                                selectionOperator)
                )
                .orElse(
                        new CustomInitializationSteadyStateGeneticAlgorithm(
                                problem,
                                resultSaver,
                                maxEvaluations,
                                populationSize,
                                crossoverOperator,
                                mutationOperator,
                                selectionOperator)
                );
    }

    public GenerationalGeneticAlgorithm<PermutationSolution<Integer>> buildAsGenerational() {
        return Optional.ofNullable(genotypeProvider)
                .map(provider ->
                        new CustomInitializationGenerationalGeneticAlgorithm(
                                provider,
                                problem,
                                resultSaver,
                                maxEvaluations,
                                populationSize,
                                crossoverOperator,
                                mutationOperator,
                                selectionOperator,
                                evaluator)
                )
                .orElse(
                        new CustomInitializationGenerationalGeneticAlgorithm(
                                problem,
                                resultSaver,
                                maxEvaluations,
                                populationSize,
                                crossoverOperator,
                                mutationOperator,
                                selectionOperator,
                                evaluator)
                );
    }

    /*
     * Getters
     */
    public Problem<PermutationSolution<Integer>> getProblem() {
        return problem;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public CrossoverOperator<PermutationSolution<Integer>> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<PermutationSolution<Integer>> getMutationOperator() {
        return mutationOperator;
    }

    public SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> getSelectionOperator() {
        return selectionOperator;
    }

    public SolutionListEvaluator<PermutationSolution<Integer>> getEvaluator() {
        return evaluator;
    }

    public GenotypeProvider getGenotypeProvider() {
        return genotypeProvider;
    }

    public void setResultSaver(BSFResultSaver resultSaver) {
        this.resultSaver = resultSaver;
    }
}
