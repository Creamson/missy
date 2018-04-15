package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import thiagodnf.jacof.aco.ElitistAntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationElitistAntSystem extends ElitistAntSystem {

    private PheromonesInitializer pheromonesInitializer;

    public CustomInitializationElitistAntSystem(PheromonesInitializer pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    public CustomInitializationElitistAntSystem(Problem problem) {
        super(problem);
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        Optional.ofNullable((pheromonesInitializer))
                .ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
