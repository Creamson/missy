package org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms;

import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class JMetal5GlobalRankEMAS<S extends Solution<?>> extends JMetal5BaseEMAS<S> {


    public JMetal5GlobalRankEMAS(Problem<S> problem, CrossoverOperator<S> crossoverOperator, MutationOperator <S>mutationOperator, MutationOperator<S> strongMutationOperator, int maxNumberOfIterations, int numberOfIslands, int envEnergy, double initialAgentResourceLevel, double transferAgentResourceLevel, String algorithmName, int replaceOnlyIfBetter, EmasDominanceComparator comparator, EmasDominanceComparator parentToChildComparator) {
        super(problem, crossoverOperator, mutationOperator, strongMutationOperator, maxNumberOfIterations, numberOfIslands, envEnergy, initialAgentResourceLevel, transferAgentResourceLevel, algorithmName,replaceOnlyIfBetter, comparator, parentToChildComparator);
    }

    @Override
    public String getAgentType() {
        return "JMetal5GlobalRankAgent";
    }



    @Override
    public String getDescription() {
        return "JMetal5GlobalRankEMAS";
    }
}
