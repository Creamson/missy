package org.uma.jmetal.algorithm.multiobjective.lemas.Comparators;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;

import java.util.Random;

public class NoisyDominanceComparator<S extends Solution<?>> extends DominanceComparator<S> {

    private final Random random;
    private final double falseNegativeRate;
    private final DominanceComparator<S> dominanceOperator;

    public NoisyDominanceComparator(DominanceComparator<S> dominanceComparator, Random random, double falseNegativeRate) {
        this.dominanceOperator = dominanceComparator;
        this.random = random;
        this.falseNegativeRate = falseNegativeRate;
    }

    @Override
    public int compare(S solution1, S solution2) {
        int comparisonResult = dominanceOperator.compare(solution1, solution2);
        if (comparisonResult != 0 && random.nextDouble() <= falseNegativeRate && falseNegativeRate > 0) {
            return -comparisonResult;
        }
        return comparisonResult;
    }
}