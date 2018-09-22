package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationAntSystem extends AntSystem {

    private Optional<PheromonesInitializer> pheromonesInitializer;

    public CustomInitializationAntSystem(Optional<PheromonesInitializer> pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        pheromonesInitializer.ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
