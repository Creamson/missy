package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.PheromonesInitializer;
import thiagodnf.jacof.aco.MaxMinAntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationMaxMinAntSystem extends MaxMinAntSystem {

    private PheromonesInitializer pheromonesInitializer;

    public CustomInitializationMaxMinAntSystem(PheromonesInitializer pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    public CustomInitializationMaxMinAntSystem(Problem problem) {
        super(problem);
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        Optional.ofNullable((pheromonesInitializer))
                .ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
