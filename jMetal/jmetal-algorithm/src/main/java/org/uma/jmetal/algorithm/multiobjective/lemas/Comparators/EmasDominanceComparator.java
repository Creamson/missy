package org.uma.jmetal.algorithm.multiobjective.lemas.Comparators;

import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5Agent;
import org.uma.jmetal.util.comparator.DominanceComparator;

import java.io.Serializable;
import java.util.Comparator;

public class EmasDominanceComparator <S extends JMetal5Agent<?>> implements Comparator<S>, Serializable {
    @Override
    public int compare(JMetal5Agent a1, JMetal5Agent a2) {
        return new DominanceComparator().compare(a1.genotype,a2.genotype);
    }
}
