package org.uma.jmetal.algorithm.impl;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.problem.Problem;

import java.util.List;

@SuppressWarnings("serial")
public abstract class AbstractEMASAlgorithm<S, R> implements Algorithm<R> {
    protected List<S> population;
    protected Problem<S> problem;

    public List<S> getPopulation() {
        return population;
    }

    public void setPopulation(List<S> population) {
        this.population = population;
    }

    public void setProblem(Problem<S> problem) {
        this.problem = problem;
    }

    public Problem<S> getProblem() {
        return problem;
    }
    public abstract int getIterations();

    protected abstract void initProgress();

    protected abstract void updateProgress();

    protected abstract void updateProgress(int iteration);

    protected abstract boolean isStoppingConditionReached();

    @Override
    public abstract R getResult();

    protected abstract void meetStep();

    protected abstract void reproStep();

    protected abstract void deadStep();

    protected abstract void createInitialPopulation();

    @Override
    public void run() {

        initProgress();
        createInitialPopulation();

        while (!isStoppingConditionReached()) {
            meetStep();
            reproStep();
            deadStep();
            updateProgress(getIterations());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}
