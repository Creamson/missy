package pl.edu.agh.missy.evo;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.PermutationSolution;

import java.util.List;

public interface JMetalEvolutionaryAlgorithm extends Algorithm<PermutationSolution<Integer>> {
    List<PermutationSolution<Integer>> getPopulation();
}
