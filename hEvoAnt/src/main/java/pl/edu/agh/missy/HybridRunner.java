package pl.edu.agh.missy;

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
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import pl.edu.agh.missy.ants.CustomInitializationAntSystem;
import pl.edu.agh.missy.convertion.SimpleLastPathBasedGenotypeProvider;
import pl.edu.agh.missy.convertion.SingleDepositionPerGenotypeInitializer;
import pl.edu.agh.missy.genetic.AlgorithmBuilder;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;
import thiagodnf.jacof.util.ExecutionStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HybridRunner {

    public static void main(String[] args) throws IOException {
        ExecutionStats stats = null;
        List<PermutationSolution<Integer>> population = null;

        for (int i = 0; i < 2; i++) {
            population = runGeneticSample(stats);
            stats = runAntSample(population);
        }
    }

    private static ExecutionStats runAntSample(List<PermutationSolution<Integer>> population) throws IOException {
        Problem tsp = new TravellingSalesmanProblem("in/kroB100.tsp");
        //ahoy
        AntSystem aco = Optional.ofNullable(population)
                .map(SingleDepositionPerGenotypeInitializer::new)
                .map(initializer -> new CustomInitializationAntSystem(initializer, tsp))
                .orElse(new CustomInitializationAntSystem(tsp));

        aco.setNumberOfAnts(10);
        aco.setNumberOfIterations(20);
        aco.setAlpha(1.0);
        aco.setBeta(2.0);
        aco.setRho(0.1);
        ExecutionStats es = ExecutionStats.execute(aco, tsp);
        es.printStats();
        aco.getGraph().getTau();

        return es;
    }

    public static List<PermutationSolution<Integer>> runGeneticSample(ExecutionStats executionStats) throws IOException {
        PermutationProblem<PermutationSolution<Integer>> problem;
        AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>> algorithm;
        CrossoverOperator<PermutationSolution<Integer>> crossover;
        MutationOperator<PermutationSolution<Integer>> mutation;
        SelectionOperator<List<PermutationSolution<Integer>>, PermutationSolution<Integer>> selection;

        problem = new TSP("/tspInstances/kroB100.tsp");

        crossover = new PMXCrossover(0.9);

        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        mutation = new PermutationSwapMutation<>(mutationProbability);

        selection = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<PermutationSolution<Integer>>());

        AlgorithmBuilder algorithmBuilder = new AlgorithmBuilder(problem, crossover, mutation)
                .setPopulationSize(100)
                .setMaxEvaluations(250000)
                .setSelectionOperator(selection);

        Optional.ofNullable(executionStats).ifPresent(stats -> {
            algorithmBuilder.setGenotypeProvider(new SimpleLastPathBasedGenotypeProvider(problem, stats));
        });

        algorithm = algorithmBuilder
                .buildAsGenerational();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();


        PermutationSolution<Integer> solution = algorithm.getResult();
        List<PermutationSolution<Integer>> population = new ArrayList<>(1);
        population.add(solution);

        long computingTime = algorithmRunner.getComputingTime();
//
        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();
//
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
        return algorithm.getPopulation();
    }
}
