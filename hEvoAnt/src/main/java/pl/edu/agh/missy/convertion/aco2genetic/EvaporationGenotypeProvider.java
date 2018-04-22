package pl.edu.agh.missy.convertion.aco2genetic;

import com.google.common.base.Preconditions;
import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.genetic.JMetalIntegerPermuationSolution;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.rule.globalupdate.evaporation.FullEvaporation;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

public class EvaporationGenotypeProvider implements GenotypeProvider {

    private final PermutationProblem<PermutationSolution<Integer>> problem;
    private final ACO aco;
    private int lastId = 0;
    private FullEvaporation evaporation;

    public EvaporationGenotypeProvider(PermutationProblem<PermutationSolution<Integer>> problem, ExecutionStats stats) {
        this(problem, stats, 0.5);
    }

    public EvaporationGenotypeProvider(PermutationProblem<PermutationSolution<Integer>> problem, ExecutionStats stats, double evaporationRate) {
        checkArgument(evaporationRate <= 1.0);
        this.problem = problem;
        this.aco = stats.aco;
        evaporation = new FullEvaporation(aco, evaporationRate);
    }

    @Override
    public PermutationSolution<Integer> nextGenotype() {
        Ant nextAnt = new Ant(aco, (lastId++) % aco.getNumberOfAnts());
        nextAnt.setAntInitialization(aco.getAntInitialization());
        nextAnt.run();
        for (int i = 0; i < nextAnt.tour.size() - 1; i++) {
            int v1 = nextAnt.tour.get(i);
            int v2 = nextAnt.tour.get(i + 1);
            double newValue = evaporation.getTheNewValue(v1, v2);
            aco.getGraph().setTau(v1, v2, newValue);
            aco.getGraph().setTau(v2, v1, newValue);
        }
        return new JMetalIntegerPermuationSolution(
                problem,
                nextAnt.tour
        );
    }
}
