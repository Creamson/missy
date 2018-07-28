//


//


package org.uma.jmetal.runner.singleobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.impl.AbstractEvolutionaryAlgorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.TumorAnalysis.Algorithm.TumorAnalysisGeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.singleobjective.TumorSensitivityProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import java.util.ArrayList;
import java.util.List;

public class TumorParallelGenerationalDoubleSolutionGeneticAlgorithmRunner {
    private static final int DEFAULT_NUMBER_OF_CORES = 100;

    /**
     * Usage: java org.uma.jmetal.runner.singleobjective.ParallelGenerationalGeneticAlgorithmRunner [cores]
     */
    public static void main(String[] args) throws Exception {
        Algorithm<DoubleSolution> algorithm;
        DoubleProblem problem = new TumorSensitivityProblem();
        int numberOfCores;
        if (args.length == 1) {
            numberOfCores = Integer.valueOf(args[0]);
        } else {
            numberOfCores = DEFAULT_NUMBER_OF_CORES;
        }

        CrossoverOperator<DoubleSolution> crossoverOperator = new SBXCrossover(0.9, 20);
//    CrossoverOperator<BinarySolution> crossoverOperator = new SinglePointCrossover(0.9) ;
        MutationOperator<DoubleSolution> mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(), 20);
//    MutationOperator<BinarySolution> mutationOperator = new BitFlipMutation(1.0 / problem.getNumberOfBits(0)) ;
//  SelectionOperator<List<DoubleSolution>,DoubleSolution> selectionOperator = new TournamentSelection<DoubleSolution>();
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selectionOperator = new BinaryTournamentSelection<DoubleSolution>();

        GeneticAlgorithmBuilder<DoubleSolution> builder = new TumorAnalysisGeneticAlgorithmBuilder<DoubleSolution>(
                problem, crossoverOperator, mutationOperator)
                .setPopulationSize(numberOfCores + 1)
                .setMaxEvaluations(420)
                .setSelectionOperator(selectionOperator)
                .setSolutionListEvaluator(new MultithreadedSolutionListEvaluator<DoubleSolution>(numberOfCores, problem));

        algorithm = builder.build();
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        builder.getEvaluator().shutdown();

        DoubleSolution solution = algorithm.getResult();
        List<DoubleSolution> population = new ArrayList<>(1);
        population.add(solution);
        population.addAll(((AbstractEvolutionaryAlgorithm) algorithm).getPopulation());

        long computingTime = algorithmRunner.getComputingTime();
        new SolutionListOutput(population)
                .setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
                .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
                .print();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
        JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
        JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
    }
}
