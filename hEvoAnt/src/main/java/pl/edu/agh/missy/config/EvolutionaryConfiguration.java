package pl.edu.agh.missy.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5ProgressiveEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.AreaUnderControlComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.AreaUnderControlDistanceToClosesNeighbourComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.singleobjective.TSP;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import pl.edu.agh.missy.convertion.aco2genetic.CoinFlipGenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.EvaporationGenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.SimpleLastPathBasedGenotypeProvider;
import pl.edu.agh.missy.evo.emas.CustomInitializationProgressiveEMAS;
import pl.edu.agh.missy.evo.emas.EMASWrapper;
import pl.edu.agh.missy.evo.emas.EmasBuilder;
import pl.edu.agh.missy.evo.genetic.AlgorithmBuilder;
import pl.edu.agh.missy.evo.JMetalEvolutionaryAlgorithm;
import pl.edu.agh.missy.results.BSFResultSaver;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
@Import({
        ResultSavingConfiguration.class
})
public class EvolutionaryConfiguration {

    @Value("${genetic.crossoverProbabilityFactor:90.0}")
    private double crossoverProbabilityFactor;
    @Value("${genetic.mutationProbabilityFactor:1.0}")
    private double mutationProbabilityFactor;
    @Value("${genetic.populationSize:100}")
    private int populationSize;
    @Value("${genetic.maxEvaluations:250000}")
    private int maxEvaluations;
    @Value("${genetic.problemPath}")
    private String problemPath;
    @Value("${genetic.evaporationGenotypeProvider.evaporationRate:1.0}")
    private double evaporationRate;
    @Value("${genetic.genotypeProviderVersion:simple}")
    private String genotypeProviderVersion;
    @Value("${genetic.algorithmType}")
    private String algorithmType;

    private final Map<String, BiFunction<PermutationProblem<PermutationSolution<Integer>>, ExecutionStats, GenotypeProvider>> genotypeProviderFactorySupplier = ImmutableMap.of(
            "simple", SimpleLastPathBasedGenotypeProvider::new,
            "coinflip", CoinFlipGenotypeProvider::new,
            "evaporation", (p, e) -> new EvaporationGenotypeProvider(p, e, evaporationRate)
    );

    private JMetalEvolutionaryAlgorithm getGeneticAlgorithm(BSFResultSaver bsfResultSaver, ExecutionStats executionStats) {
        PermutationProblem<PermutationSolution<Integer>> problem;

        CrossoverOperator<PermutationSolution<Integer>> crossover;
        MutationOperator<PermutationSolution<Integer>> mutation;
        SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selection;

        problem = getProblemInstance();

        crossover = new PMXCrossover(crossoverProbabilityFactor / problem.getNumberOfVariables());

        double mutationProbability = mutationProbabilityFactor / problem.getNumberOfVariables();
        mutation = new PermutationSwapMutation<>(mutationProbability);

        selection = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());

        AlgorithmBuilder algorithmBuilder = new AlgorithmBuilder(problem, crossover, mutation)
                .setPopulationSize(populationSize)
                .setMaxEvaluations(maxEvaluations)
                .setSelectionOperator(selection);

        Optional.ofNullable(executionStats).ifPresent(stats ->
                algorithmBuilder.setGenotypeProvider(
                        genotypeProviderFactorySupplier.get(genotypeProviderVersion).apply(problem, stats)
                )
        );

        if (algorithmType.equalsIgnoreCase("generational")) {
            return algorithmBuilder
                    .buildAsGenerational();
        } else if (algorithmType.equalsIgnoreCase("steadystate")) {
            return algorithmBuilder
                    .buildAsSteadyState();
        } else if (algorithmType.equalsIgnoreCase("baseEmas")
                || algorithmType.equalsIgnoreCase("globalRankEmas")
                || algorithmType.equalsIgnoreCase("progressiveEmas")
                ) {
            return getEmasInstance(algorithmType, bsfResultSaver, executionStats, problem, crossover, mutation);
        } else {
            throw new IllegalStateException("No known algorithm type specified");
        }
    }

    private JMetalEvolutionaryAlgorithm getEmasInstance(String emasType, BSFResultSaver bsfResultSaver, ExecutionStats executionStats, PermutationProblem<PermutationSolution<Integer>> problem, CrossoverOperator<PermutationSolution<Integer>> crossover, MutationOperator<PermutationSolution<Integer>> mutation) {
        MutationOperator<PermutationSolution<Integer>> strongMutationOperator = new PermutationSwapMutation<>(1.0);
        int maxIterations = Constants.MAX_ITERATIONS;
//        int maxIterations = 1000;
        int numberOfIslands = Constants.NUMBER_OF_ISLANDS;
        int envEnergy = Constants.ENV_ENERGY;
        double initialResourceValue = Constants.INITIAL_RESOURCE_VALUE;
        double transferResourceValue = Constants.TRANSFER_RESOURCE_VALUE;
        AreaUnderControlDistanceToClosesNeighbourComparator comparator = new AreaUnderControlDistanceToClosesNeighbourComparator();
        AreaUnderControlComparator parentToChildComparator = new AreaUnderControlComparator();


        Optional<GenotypeProvider> genotypeProvider = Optional.ofNullable(executionStats)
                .map(stats -> genotypeProviderFactorySupplier.get(genotypeProviderVersion).apply(problem, stats));

        EmasBuilder emasBuilder = new EmasBuilder()
                .withAlgorithmName(algorithmType)
                .withComparator(comparator)
                .withCrossoverOperator(crossover)
                .withEnvEnergy(envEnergy)
                .withInitialAgentResourceLevel(initialResourceValue)
                .withInitialGenotypeProvider(genotypeProvider)
                .withMaxNumberOfIterations(maxIterations)
                .withMutationOperator(mutation)
                .withNumberOfIslands(numberOfIslands)
                .withParentToChildComparator(parentToChildComparator)
                .withProblem(problem)
                .withReplaceOnlyIfBetter(2)
                .withResultSaver(bsfResultSaver)
                .withStrongMutationOperator(strongMutationOperator)
                .withTransferAgentResourceLevel(transferResourceValue);

        if ("progressiveEmas".equalsIgnoreCase(emasType)) {
            return emasBuilder
                    .buildAsProgressive();
        } else if ("globalRankEmas".equalsIgnoreCase(emasType)) {
            return emasBuilder
                    .buildAsGlobalRank();
        } else if ("baseEmas".equalsIgnoreCase(emasType)) {
            return emasBuilder
                    .buildAsBase();
        } else {
            throw new IllegalStateException("Something got totally messed up because such emas doesn't exist");
        }
    }

    private PermutationProblem<PermutationSolution<Integer>> getProblemInstance() {
        PermutationProblem<PermutationSolution<Integer>> problem;
        try {
            problem = new TSP(problemPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return problem;
    }

    @Bean
    public Function<ExecutionStats, JMetalEvolutionaryAlgorithm> getEvolutionaryAlgorithmFactory(BSFResultSaver bsfResultSaver) {
        return executionStats -> getGeneticAlgorithm(bsfResultSaver, executionStats);
    }
}
