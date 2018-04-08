package pl.edu.agh.missy.convertion;

import org.uma.jmetal.solution.PermutationSolution;

public interface GenotypeProvider {
    PermutationSolution<Integer> nextGenotype();
}
