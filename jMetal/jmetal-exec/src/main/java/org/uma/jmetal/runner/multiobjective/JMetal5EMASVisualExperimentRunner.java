package org.uma.jmetal.runner.multiobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5ProgressiveEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.AreaUnderControlComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.AreaUnderControlDistanceToClosesNeighbourComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.algorithm.multiobjective.lemas.Visualization.ChartWrapper;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;

public class JMetal5EMASVisualExperimentRunner extends AbstractAlgorithmRunner {

    public static void main(String[] args) throws JMetalException {

        List<Algorithm> algorithmsToRun = new ArrayList<>();

//        algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "BaseEMAS", 0, new EmasDominanceComparator()));

//        algorithmsToRun.add(new JMetal5ProgressiveEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "JMetalProgressiveEMAS", 0, new EmasDominanceComparator()));


//                algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "AreaUnderControl", 0, new AreaUnderControlComparator()));
//

//        algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "AreaUnderControlWithDistance", 0, new AreaUnderControlDistanceToClosesNeighbourComparator(), new EmasDominanceComparator()));



//        algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "AreaUnderControlWithDistance", 0, new AreaUnderControlDistanceToClosesNeighbourComparator(), new EmasDominanceComparator()));

//        algorithmsToRun.add(new JMetal5ProgressiveEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveAreaUnderControl", 0, new AreaUnderControlComparator()));

//        algorithmsToRun.add(new JMetal5ProgressiveEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveEliteAreaUnderControl", 1, new AreaUnderControlComparator()));

//        algorithmsToRun.add(new JMetal5ProgressiveEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveEliteAreaUnderControlWithDistance", 1, new AreaUnderControlDistanceToClosesNeighbourComparator()));

//        algorithmsToRun.add(new JMetal5ProgressiveEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveEliteAreaUnderControlWithDistanceOnlyDominance", 2, new AreaUnderControlDistanceToClosesNeighbourComparator(), new AreaUnderControlComparator()));

        algorithmsToRun.add(new JMetal5ProgressiveEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveEliteAreaUnderControlWithDistanceArea", 2, new AreaUnderControlDistanceToClosesNeighbourComparator(), new AreaUnderControlComparator()));

//        algorithmsToRun.add(new JMetal5BaseEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "AlphaDominance", 0, new AlphaDominanceComparator(Constants.ALPHA_VALUES), new EmasDominanceComparator()));


//        algorithmsToRun.add(new JMetal5ProgressiveEMAS(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE, "ProgresiveAreaUnderControlWithDistance", 0, new AreaUnderControlDistanceToClosesNeighbourComparator()));

//        Algorithm<List<DoubleSolution>> algorithm;

//        algorithmsToRun.add(new CustomMeasuredNSGAII(Constants.PROBLEM.getProblem(),
//                Constants.MAX_ITERATIONS,
//                100,
//                Constants.XOP,
//                Constants.MOP,
//                new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>()),
//                new DominanceComparator<>(),
//                new SequentialSolutionListEvaluator<>()
//                ));

//        algorithmsToRun.add(new NSGAIIBuilder<DoubleSolution>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP)
//                .setSelectionOperator(new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>()))
//                .setMaxEvaluations(25000)
//                .setPopulationSize(100)
//                .setVariant(NSGAIIBuilder.NSGAIIVariant.Measures)
//                .build());



//        algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(),
//                new SBXCrossover(1.0, 5),
//                new PolynomialMutation(1.0 / Constants.PROBLEM.getProblem().getNumberOfVariables(), 10.0),
//                new PolynomialMutation(1.0, 20.0),
//                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
//                Constants.TRANSFER_RESOURCE_VALUE,
//                "JMetalBaseEMASElite",0, new EmasDominanceComparator()));


//        ChartWrapper chartWrapper = new ChartWrapper(algorithmsToRun,Constants.NUMBER_OF_DECISION_VARIABLES_TO_SHOW);
//        algorithmsToRun.forEach(algorithm -> ((Measurable) algorithm).getMeasureManager()
//                .getPushMeasure("currentPopulation")
//                .register(population ->
//                chartWrapper.updateChart((List<DoubleSolution>) population, algorithm.getName(),(JMetal5BaseEMAS) algorithm,algorithmsToRun.indexOf(algorithm))));


        ChartWrapper chartWrapper = new ChartWrapper(algorithmsToRun,Constants.NUMBER_OF_DECISION_VARIABLES_TO_SHOW);
        algorithmsToRun.forEach(algorithm -> ((Measurable) algorithm).getMeasureManager()
                .getPushMeasure("currentPopulation")
                .register(population ->
                        chartWrapper.updateChart((List<DoubleSolution>) population, algorithm.getName(),algorithm,algorithmsToRun.indexOf(algorithm))));


        algorithmsToRun
                .parallelStream()
                .forEach(algorithm -> new AlgorithmRunner.Executor(algorithm)
                        .execute());


    }
}
