package org.uma.jmetal.runner.singleobjective;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5BaseEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasSingleObjectiveComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.algorithm.multiobjective.lemas.Visualization.SingleObjectiveChartWrapper;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;

public class JMetal5EMASSingleObjectiveVisualExperimentRunner extends AbstractAlgorithmRunner {

    public static void main(String[] args) throws JMetalException {

        List<Algorithm> algorithmsToRun = new ArrayList<>();

        algorithmsToRun.add(new JMetal5BaseEMAS<>(Constants.PROBLEM.getProblem(), Constants.XOP, Constants.MOP, Constants.STRONG_MOP,
                Constants.MAX_ITERATIONS, Constants.NUMBER_OF_ISLANDS, Constants.ENV_ENERGY, Constants.INITIAL_RESOURCE_VALUE,
                Constants.TRANSFER_RESOURCE_VALUE, "BaseEMAS", 1, new EmasSingleObjectiveComparator(), new EmasSingleObjectiveComparator()));


        SingleObjectiveChartWrapper chartWrapper = new SingleObjectiveChartWrapper(algorithmsToRun, Constants.NUMBER_OF_DECISION_VARIABLES_TO_SHOW);
        algorithmsToRun.forEach(algorithm -> ((Measurable) algorithm).getMeasureManager()
                .getPushMeasure("currentPopulation")
                .register(population ->
                        chartWrapper.updateChart((List<DoubleSolution>) population, algorithm.getName(), (JMetal5BaseEMAS) algorithm, algorithmsToRun.indexOf(algorithm))));

        algorithmsToRun
                .parallelStream()
                .forEach(algorithm -> new AlgorithmRunner.Executor(algorithm)
                        .execute());


    }
}
