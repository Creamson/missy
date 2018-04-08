package pl.edu.agh.missy.convertion;

import org.apache.commons.lang3.mutable.MutableInt;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.aco.graph.AntGraph;
import thiagodnf.jacof.aco.rule.globalupdate.deposit.PartialDeposit;
import thiagodnf.jacof.aco.subset.AbstractSubSet;
import thiagodnf.jacof.problem.Problem;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SingleDepositionPerGenotypeInitializer implements PheromonesInitializer {

    private final List<PermutationSolution<Integer>> population;

    public SingleDepositionPerGenotypeInitializer(List<PermutationSolution<Integer>> population) {
        this.population = population;
    }

    @Override
    public void initializePheromones(ACO aco) {
        List<Ant> initializationAnts = createAntsWithPathsFromGenotypes(aco);

        PartialDeposit deposit = new PartialDeposit(aco, 1.0, new InitializationAntSet(aco, initializationAnts));
        depositInitialPheromones(aco, deposit);
    }

    private void depositInitialPheromones(ACO aco, PartialDeposit deposit) {
        Problem problem = aco.getProblem();
        AntGraph graph = aco.getGraph();

        for (int i = 0; i < problem.getNumberOfNodes(); i++) {
            for (int j = i; j < problem.getNumberOfNodes(); j++) {
                if (i != j) {
                    double newVal = deposit.getTheNewValue(i, j);
                    graph.setTau(i, j, deposit.getTheNewValue(i, j));
                    graph.setTau(j, i, graph.getTau(i, j));
                }
            }
        }
    }

    private List<Ant> createAntsWithPathsFromGenotypes(ACO aco) {
        MutableInt antIds = new MutableInt();
        return population.stream()
                .map(genotype -> {
                    Ant ant = new Ant(aco, antIds.getAndIncrement());
                    DefaultIntegerPermutationSolution path = (DefaultIntegerPermutationSolution) genotype;
                    int[] solution = new int[path.getNumberOfVariables()];
                    IntStream.range(0, path.getNumberOfVariables() - 1).forEach(index -> {
                        int v1 = path.getVariableValue(index);
                        int v2 = path.getVariableValue(index + 1);
                        ant.path[v1][v2] = 1;
                        ant.path[v2][v1] = 1;
                        solution[index] = path.getVariableValue(index);
                    });
                    solution[path.getNumberOfVariables()-1] = path.getVariableValue(path.getNumberOfVariables()-1);
                    ant.setTourLength(aco.getProblem().evaluate(solution));
                    return ant;
                })
                .collect(toList());
    }


    private class InitializationAntSet extends AbstractSubSet {

        private final List<Ant> ants;

        InitializationAntSet(ACO aco, List<Ant> ants) {
            super(aco);
            this.ants = ants;
        }

        @Override
        public List<Ant> getSubSet() {
            return ants;
        }

        @Override
        public String toString() {
            return InitializationAntSet.class.getSimpleName();
        }
    }
}
