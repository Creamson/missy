package org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.TumorAnalysis.Algorithm;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GeneticAlgorithmBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class TumorAnalysisGeneticAlgorithmBuilder<S extends Solution<?>> extends GeneticAlgorithmBuilder<S> {




    public TumorAnalysisGeneticAlgorithmBuilder(Problem<S> problem,
                                                CrossoverOperator<S> crossoverOperator,
                                                MutationOperator<S> mutationOperator) {
        super(problem, crossoverOperator, mutationOperator);
    }

    @Override
    public Algorithm<S> build() {
        return new TumorAnalysisGenerationalGeneticAlgorithm<S>(super.getProblem(), super.getMaxEvaluations(),
                super.getPopulationSize(), super.getCrossoverOperator(),
                super.getMutationOperator(), super.getSelectionOperator(), super.getEvaluator());
    }
}
