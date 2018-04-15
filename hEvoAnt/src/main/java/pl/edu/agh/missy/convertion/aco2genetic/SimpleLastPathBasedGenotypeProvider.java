package pl.edu.agh.missy.convertion.aco2genetic;

import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.genetic.JMetalIntegerPermuationSolution;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SimpleLastPathBasedGenotypeProvider implements GenotypeProvider {

    private final List<List<Integer>> antPaths;
    private int nextIndex = 0;
    private PermutationProblem<PermutationSolution<Integer>> problem;


    public SimpleLastPathBasedGenotypeProvider(PermutationProblem<PermutationSolution<Integer>> problem, ExecutionStats acoExecutionStats) {
        antPaths = Stream.of(acoExecutionStats.aco.getAnts())
                .map(Ant::getSolution)
                .map(path -> Arrays.stream(path).boxed().collect(toList()))
                .collect(toList());
        this.problem = problem;
    }

    @Override
    public PermutationSolution<Integer> nextGenotype() {
        PermutationSolution<Integer> genotype = new JMetalIntegerPermuationSolution(problem, antPaths.get(nextIndex));
        nextIndex = (nextIndex + 1) % antPaths.size();
        return genotype;
    }
}
