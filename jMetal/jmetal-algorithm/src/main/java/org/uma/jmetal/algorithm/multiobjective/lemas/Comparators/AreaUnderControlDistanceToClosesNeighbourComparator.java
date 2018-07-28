package org.uma.jmetal.algorithm.multiobjective.lemas.Comparators;

import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5Agent;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;

import java.util.List;

public class AreaUnderControlDistanceToClosesNeighbourComparator<S extends JMetal5Agent<?>> extends AreaUnderControlComparator<S> {


    @Override
    public int compare(JMetal5Agent agent1, JMetal5Agent agent2) {
        int isBetter = super.compare(agent1,agent2);
               if (isBetter == 0) {
                 if (agent1.getDistanceToClosestNeighbour()==agent2.getDistanceToClosestNeighbour())
                     return 0;
                 else
                   return (agent1.getDistanceToClosestNeighbour() < agent2.getDistanceToClosestNeighbour()) ?  1 : -1;
               }

               return isBetter;
    }

}