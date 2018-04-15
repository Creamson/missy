package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationAntSystem extends AntSystem {

    private PheromonesInitializer pheromonesInitializer;

    public CustomInitializationAntSystem(PheromonesInitializer pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    public CustomInitializationAntSystem(Problem problem) {
        super(problem);
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        Optional.ofNullable((pheromonesInitializer))
                .ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
