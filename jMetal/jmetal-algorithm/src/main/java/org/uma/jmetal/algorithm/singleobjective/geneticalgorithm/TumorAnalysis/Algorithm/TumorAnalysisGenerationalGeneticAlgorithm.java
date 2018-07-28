package org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.TumorAnalysis.Algorithm;

import org.uma.jmetal.algorithm.singleobjective.geneticalgorithm.GenerationalGeneticAlgorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.TumorSensitivityProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.List;

public class TumorAnalysisGenerationalGeneticAlgorithm<S extends Solution<?>> extends GenerationalGeneticAlgorithm<S> {
  public TumorAnalysisGenerationalGeneticAlgorithm(Problem<S> problem, int maxEvaluations, int populationSize,
                                                   CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                                                   SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator) {
    super(problem,maxEvaluations,populationSize,crossoverOperator,mutationOperator,selectionOperator,evaluator);
  }





  @Override
  protected List<S> createInitialPopulation() {
    TumorSensitivityProblem problem = (TumorSensitivityProblem) getProblem();
    List<S> population = super.createInitialPopulation();
    problem.setRuntimeLowerLimit();
    problem.setRuntimeUpperLimit();
    return population;
  }

  @Override
  public S getResult() {
    return super.getResult();
  }

  @Override public String getName() {
    return "TAgGA" ;
  }

  @Override public String getDescription() {
    return "Tumor Analysis Generational Genetic Algorithm" ;
  }
}
