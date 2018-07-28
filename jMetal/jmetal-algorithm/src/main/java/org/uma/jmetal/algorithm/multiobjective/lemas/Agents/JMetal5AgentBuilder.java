package org.uma.jmetal.algorithm.multiobjective.lemas.Agents;

import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5BaseEMAS;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.Solution;

import java.util.Optional;

public final class JMetal5AgentBuilder<S extends Solution<?>> {

    private JMetal5BaseEMAS EMAS;

    private S genotype;

    private int currentIsland = 0;

    private double initialResourcesValue;

    private String type;

    private CrossoverOperator crossover;

    private EmasDominanceComparator comparator;

    private EmasDominanceComparator parentToChildComparator;

    public JMetal5AgentBuilder<S> withGenotype(S genotype) {
        this.genotype = genotype;
        return this;
    }

    public JMetal5AgentBuilder<S> withEMAS(JMetal5BaseEMAS EMAS) {
        this.EMAS = EMAS;
        return this;
    }


    public JMetal5AgentBuilder<S> withCrossover(CrossoverOperator crossover) {
        this.crossover = crossover;
        return this;
    }


    public JMetal5AgentBuilder<S> withAgentType(String type) {
        this.type = type;
        return this;
    }

    public JMetal5AgentBuilder<S> withCurrentIsland(int currentIsland) {
        this.currentIsland = currentIsland;
        return this;
    }

    public JMetal5AgentBuilder<S> withDominanceComparator(EmasDominanceComparator comparator) {
        this.comparator = comparator;
        return this;
    }

    public JMetal5AgentBuilder<S> withParentToChildComparator(EmasDominanceComparator parentToChildComparator) {
        this.parentToChildComparator = parentToChildComparator;
        return this;
    }


    public JMetal5AgentBuilder<S> withInitialResourcesValue(double initialResourcesValue) {
        this.initialResourcesValue = initialResourcesValue;
        return this;
    }

    public JMetal5Agent<S> build() {
        JMetal5Agent<S> agent;
        switch (Optional.ofNullable(type).orElse("")) {
            default:
            case "JMetal5BaseAgent":
                agent = new JMetal5Agent<>();
                break;
            case "JMetal5GlobalRankAgent":
                agent = new JMetal5GlobalRankAgent();
                break;
            case "JMetal5ProgressiveAgent":
                agent = new JMetal5ProgressiveAgent();
                break;
        }
        agent.setGenotype(genotype);
        agent.setCurrentIsland(currentIsland);
        agent.setResourceLevel(initialResourcesValue);
        agent.setCrossover(crossover);
        agent.setEMAS(EMAS);
        agent.setComparator(comparator);
        agent.setParentToChildComparator(parentToChildComparator);
        return agent;
    }
}