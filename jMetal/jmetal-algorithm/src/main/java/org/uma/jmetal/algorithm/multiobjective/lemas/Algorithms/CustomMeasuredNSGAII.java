package org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.measure.impl.SimpleMeasureManager;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.front.imp.ArrayFront;

import java.util.Comparator;
import java.util.List;

public class CustomMeasuredNSGAII<S extends Solution<?>> extends NSGAII<S> implements Measurable {
    private SimpleMeasureManager measureManager = new SimpleMeasureManager();
    private BasicMeasure<Object> solutionListMeasure;
    public int iterations;

    /* Measures code */
    private void initMeasures() {
        solutionListMeasure = new BasicMeasure<>() ;
        measureManager.setPushMeasure("currentPopulation", solutionListMeasure);
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    protected void updateProgress() {
        iterations++;
        solutionListMeasure.push(getPopulation());
    }

    @Override protected boolean isStoppingConditionReached() {
        return iterations >= maxEvaluations;
    }

    protected void updateProgress(int iteration) {
        iterations++;
        solutionListMeasure.push(getPopulation());
    }

    @Override
    protected void initProgress() {
        iterations = 0;

    }



    public CustomMeasuredNSGAII(Problem<S> problem, int maxIterations, int populationSize,
                                CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                                SelectionOperator<List<S>, S> selectionOperator, Comparator<S> dominanceComparator, SolutionListEvaluator<S> evaluator) {
        super(problem, maxIterations, populationSize, crossoverOperator, mutationOperator,
                selectionOperator, dominanceComparator, evaluator) ;

//        referenceFront = new ArrayFront() ;

        initMeasures() ;
    }


    @Override
    public MeasureManager getMeasureManager() {
        return measureManager;
    }
}
