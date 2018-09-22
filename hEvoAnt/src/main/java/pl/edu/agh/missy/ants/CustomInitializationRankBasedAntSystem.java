package pl.edu.agh.missy.ants;

import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import thiagodnf.jacof.aco.RankBasedAntSystem;
import thiagodnf.jacof.problem.Problem;

import java.util.Optional;

public class CustomInitializationRankBasedAntSystem extends RankBasedAntSystem {

    private Optional<PheromonesInitializer> pheromonesInitializer;

    public CustomInitializationRankBasedAntSystem(Optional<PheromonesInitializer> pheromonesInitializer, Problem problem) {
        super(problem);
        this.pheromonesInitializer = pheromonesInitializer;
    }

    @Override
    protected void initializePheromones() {
        super.initializePheromones();
        pheromonesInitializer.ifPresent(initializer -> initializer.initializePheromones(this));
    }
}
