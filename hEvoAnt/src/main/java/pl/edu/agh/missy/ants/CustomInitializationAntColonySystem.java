package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import thiagodnf.jacof.aco.AntColonySystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationAntColonySystem extends AntColonySystem {

    private PheromonesInitializer pheromonesInitializer;

    public CustomInitializationAntColonySystem(PheromonesInitializer pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    public CustomInitializationAntColonySystem(Problem problem) {
        super(problem);
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        Optional.ofNullable((pheromonesInitializer))
                .ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
