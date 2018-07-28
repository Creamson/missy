package org.uma.jmetal.algorithm.multiobjective.lemas.Comparators;

import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5Agent;
import org.uma.jmetal.util.comparator.ObjectiveComparator;

public class EmasSingleObjectiveComparator<S extends JMetal5Agent<?>> extends EmasDominanceComparator<S> {

    @Override
    public int compare(JMetal5Agent a1, JMetal5Agent a2) {
        return new ObjectiveComparator(0).compare(a1.genotype, a2.genotype);
    }
}
