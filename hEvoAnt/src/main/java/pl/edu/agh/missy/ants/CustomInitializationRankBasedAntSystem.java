package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.PheromonesInitializer;
import thiagodnf.jacof.aco.RankBasedAntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationRankBasedAntSystem extends RankBasedAntSystem {

    private PheromonesInitializer pheromonesInitializer;

    public CustomInitializationRankBasedAntSystem(PheromonesInitializer pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    public CustomInitializationRankBasedAntSystem(Problem problem) {
        super(problem);
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        Optional.ofNullable((pheromonesInitializer))
                .ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
