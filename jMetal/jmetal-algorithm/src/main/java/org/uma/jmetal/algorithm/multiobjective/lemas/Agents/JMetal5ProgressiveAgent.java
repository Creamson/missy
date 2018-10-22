package org.uma.jmetal.algorithm.multiobjective.lemas.Agents;

import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.solution.Solution;


public class JMetal5ProgressiveAgent<S extends Solution<?>> extends JMetal5Agent<S> {

//    public JMetal5ProgressiveAgent(Solution genotype, CrossoverOperator crossover, double resourceLevel, int currentIsland, EmasDominanceComparator comparator, JMetal5BaseEMAS EMAS) {
//        super(genotype, crossover, resourceLevel, currentIsland, comparator, EMAS);
//    }

    @Override
    public boolean canReproduce()
    {
        return getResourceLevel() >= Constants.PROGRESSIVE_REPRODUCTION_LEVEL_VALUE;
    }



    @Override
    public String getAgentType() {
        return "JMetal5ProgressiveAgent";
    }
}
