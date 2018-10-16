package org.uma.jmetal.algorithm.multiobjective.lemas.Comparators;

import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5Agent;

import java.util.List;

public class AreaUnderControlComparator<S extends JMetal5Agent<?>> extends EmasDominanceComparator<S> {


    @Override
    public int compare(JMetal5Agent agent1, JMetal5Agent agent2) {
        int isBetter = super.compare(agent1, agent2);

        if (isBetter == 0) {
            agent1.updateKnownNonDominatedObjectives(agent2);
            isBetter = -isPartnerUnderControl(agent1, agent2);
            if (isBetter == 0) {
                isBetter = isPartnerUnderControl(agent2, agent1);
            }
        }
        return isBetter;
    }


    private int isPartnerUnderControl(JMetal5Agent agent1, JMetal5Agent agent2) {
        for (List<Double> l : (List<List<Double>>) agent1.getKnownNonDominatedObjectiveValues()) {
            int isPartnerUnderControl = 1;
            for (int i = 0; i < agent1.getEMAS().getProblem().getNumberOfObjectives(); i++) {
                if (agent2.genotype.getObjective(i) <= l.get(i)) {   //FIXME: tu jest strzal do poprawy
                    isPartnerUnderControl = 0;
                }
            }
            if (isPartnerUnderControl == 1) {
                return 1;
            }
        }
        return 0;
    }

}