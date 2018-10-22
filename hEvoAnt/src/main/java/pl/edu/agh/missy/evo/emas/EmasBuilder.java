package pl.edu.agh.missy.evo.emas;

import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.evo.JMetalEvolutionaryAlgorithm;
import pl.edu.agh.missy.results.BSFResultSaver;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.Optional;
import java.util.function.Supplier;

public class EmasBuilder {

    private Optional<GenotypeProvider> initialGenotypeProvider;
    private Problem<PermutationSolution<Integer>> problem;
    private BSFResultSaver resultSaver;
    private CrossoverOperator<PermutationSolution<Integer>> crossoverOperator;
    private MutationOperator<PermutationSolution<Integer>> mutationOperator;
    private MutationOperator<PermutationSolution<Integer>> strongMutationOperator;
    private int maxNumberOfIterations;
    private int numberOfIslands;
    private int envEnergy;
    private double initialAgentResourceLevel;
    private double transferAgentResourceLevel;
    private String algorithmName;
    private int replaceOnlyIfBetter;
    private EmasDominanceComparator comparator;
    private EmasDominanceComparator parentToChildComparator;

    public EmasBuilder  withInitialGenotypeProvider(Optional<GenotypeProvider> initialGenotypeProvider) {
        this.initialGenotypeProvider = initialGenotypeProvider;
        return this;
    }
    public EmasBuilder  withProblem(Problem<PermutationSolution<Integer>> problem) {
        this.problem = problem;
        return this;
    }
    public EmasBuilder  withResultSaver(BSFResultSaver resultSaver) {
        this.resultSaver = resultSaver;
        return this;
    }
    public EmasBuilder  withCrossoverOperator(CrossoverOperator<PermutationSolution<Integer>> crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }
    public EmasBuilder  withMutationOperator(MutationOperator<PermutationSolution<Integer>> mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }
    public EmasBuilder  withStrongMutationOperator(MutationOperator<PermutationSolution<Integer>> strongMutationOperator) {
        this.strongMutationOperator = strongMutationOperator;
        return this;
    }
    public EmasBuilder  withMaxNumberOfIterations(int maxNumberOfIterations) {
        this.maxNumberOfIterations = maxNumberOfIterations;
        return this;
    }
    public EmasBuilder  withNumberOfIslands(int numberOfIslands) {
        this.numberOfIslands = numberOfIslands;
        return this;
    }
    public EmasBuilder  withEnvEnergy(int envEnergy) {
        this.envEnergy = envEnergy;
        return this;
    }
    public EmasBuilder  withInitialAgentResourceLevel(double initialAgentResourceLevel) {
        this.initialAgentResourceLevel = initialAgentResourceLevel;
        return this;
    }
    public EmasBuilder  withTransferAgentResourceLevel(double transferAgentResourceLevel) {
        this.transferAgentResourceLevel = transferAgentResourceLevel;
        return this;
    }
    public EmasBuilder  withAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
        return this;
    }
    public EmasBuilder  withReplaceOnlyIfBetter(int replaceOnlyIfBetter) {
        this.replaceOnlyIfBetter = replaceOnlyIfBetter;
        return this;
    }
    public EmasBuilder  withComparator(EmasDominanceComparator comparator) {
        this.comparator = comparator;
        return this;
    }
    public EmasBuilder  withParentToChildComparator(EmasDominanceComparator parentToChildComparator) {
        this.parentToChildComparator = parentToChildComparator;
        return this;
    }

    public JMetalEvolutionaryAlgorithm buildAsProgressive() {
        return new EMASWrapper(initialGenotypeProvider
                .map(genotypeProvider -> new CustomInitializationProgressiveEMAS(genotypeProvider, problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator))
                .orElseGet(() -> new CustomInitializationProgressiveEMAS(problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator)));
    }

    public JMetalEvolutionaryAlgorithm buildAsGlobalRank() {
        return new EMASWrapper(initialGenotypeProvider
                .map(genotypeProvider -> new CustomInitializationGlobalRankEMAS(genotypeProvider, problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator))
                .orElseGet(() -> new CustomInitializationGlobalRankEMAS(problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator)));
    }

    public JMetalEvolutionaryAlgorithm buildAsBase() {
        return new EMASWrapper(initialGenotypeProvider
                .map(genotypeProvider -> new CustomInitializationBaseEMAS(genotypeProvider, problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator))
                .orElseGet(() -> new CustomInitializationBaseEMAS(problem, resultSaver, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName, replaceOnlyIfBetter, comparator, parentToChildComparator)));
    }
}