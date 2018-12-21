package pl.edu.agh.missy.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.problem.singleobjective.TSP;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import pl.edu.agh.missy.convertion.aco2genetic.CoinFlipGenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.EvaporationGenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.convertion.aco2genetic.SimpleLastPathBasedGenotypeProvider;
import pl.edu.agh.missy.genetic.AlgorithmBuilder;
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
//@PropertySource("classpath:application.properties")
@Import({
        ResultSavingConfiguration.class
})
public class GeneticConfiguration {

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
    @Value("${genetic.coinFlip.acceptanceProbability:0.5}")
    private double coinFlipAcceptanceProbability;

    private final Map<String, BiFunction<PermutationProblem<PermutationSolution<Integer>>, ExecutionStats, GenotypeProvider>> genotypeProviderFactorySupplier = ImmutableMap.of(
            "simple", SimpleLastPathBasedGenotypeProvider::new,
            "coinflip", (problem, stats) -> new CoinFlipGenotypeProvider(problem, stats, coinFlipAcceptanceProbability),
            "evaporation", (p, e) -> new EvaporationGenotypeProvider(p, e, evaporationRate)
    );

    private AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>> getGeneticAlgorithm(BSFResultSaver bsfResultSaver, ExecutionStats executionStats) {
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
        algorithmBuilder.setResultSaver(bsfResultSaver);

        if(algorithmType.toLowerCase().equals("generational")) {
            return algorithmBuilder
                    .buildAsGenerational();
        } else if(algorithmType.toLowerCase().equals("steadystate")) {
            return algorithmBuilder
                    .buildAsSteadyState();
        } else {
            throw new IllegalStateException("No known algorithm type specified");
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
    public Function<ExecutionStats, AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>>> getGeneticAlgorithmFactory(BSFResultSaver bsfResultSaver) {
        return executionStats -> getGeneticAlgorithm(bsfResultSaver, executionStats);
    }
}