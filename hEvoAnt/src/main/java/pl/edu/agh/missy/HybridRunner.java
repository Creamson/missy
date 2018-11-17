package pl.edu.agh.missy;

import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import pl.edu.agh.missy.results.BSFResultSaver;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class HybridRunner {

    private final Function<List<PermutationSolution<Integer>>, ACO> acoSystemFactory;
    private final Function<ExecutionStats, AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>>> geneticAlgorithmFactory;
    private final int numberOfIterations;
    private final BSFResultSaver resultSaver;
    private final boolean shouldRunAco;
    private final boolean shouldRunEvo;

    public HybridRunner(
            Function<List<PermutationSolution<Integer>>, ACO> acoSystemFactory,
            Function<ExecutionStats, AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>>> geneticAlgorithmFactory,
            int numberOfIterations,
            BSFResultSaver resultSaver,
            boolean shouldRunAco,
            boolean shouldRunEvo
    ) {
        this.acoSystemFactory = acoSystemFactory;
        this.geneticAlgorithmFactory = geneticAlgorithmFactory;
        this.numberOfIterations = numberOfIterations;
        this.resultSaver = resultSaver;
        this.shouldRunAco = shouldRunAco;
        this.shouldRunEvo = shouldRunEvo;
    }

    public void run() throws IOException {
        ExecutionStats stats = null;
        List<PermutationSolution<Integer>> population = null;

        resultSaver.startTracking();

        for (int i = 0; i < numberOfIterations; i++) {
            if (shouldRunAco) {
                stats = runAntSample(population);
                resultSaver.flushResults();
            }
            if (shouldRunEvo) {
                population = runGeneticSample(stats);
                resultSaver.flushResults();
            }
        }
    }

    private ExecutionStats runAntSample(List<PermutationSolution<Integer>> population) {
        ACO aco = acoSystemFactory.apply(population);

        return ExecutionStats.execute(aco, aco.getProblem());
    }

    private List<PermutationSolution<Integer>> runGeneticSample(ExecutionStats executionStats) {
        AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>> algorithm = geneticAlgorithmFactory.apply(executionStats);

        new AlgorithmRunner.Executor(algorithm)
                .execute();

        return algorithm.getPopulation();
    }
}
