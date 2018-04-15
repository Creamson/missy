package pl.edu.agh.missy.convertion.aco2genetic;

import org.uma.jmetal.solution.PermutationSolution;

public interface GenotypeProvider {
    PermutationSolution<Integer> nextGenotype();
}
