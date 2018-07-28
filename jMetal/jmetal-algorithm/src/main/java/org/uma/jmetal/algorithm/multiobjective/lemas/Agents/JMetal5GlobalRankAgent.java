package org.uma.jmetal.algorithm.multiobjective.lemas.Agents;

import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import java.util.List;
import java.util.stream.Collectors;


public class JMetal5GlobalRankAgent extends JMetal5Agent {


//    public JMetal5GlobalRankAgent(Solution genotype, CrossoverOperator crossover, double resourceLevel, int currentIsland, EmasDominanceComparator comparator, JMetal5BaseEMAS EMAS) {
//        super(genotype, crossover, resourceLevel, currentIsland, comparator, EMAS);
//    }

    private RankingComparator comparator = new RankingComparator();
    private Ranking<DoubleSolution> ranking = new DominanceRanking<>();


    private Ranking<DoubleSolution> computeDominanceRanking(List<? extends JMetal5Agent> population) {
        List solutionList = population.stream().map(p -> p.getGenotype()).collect(Collectors.toList());
        ranking.computeRanking(solutionList);
        return ranking;
    }

    @Override
    public int doMeeting(List meetPopulation, double transferResourceValue) {
        int domResult = super.doMeeting(meetPopulation, transferResourceValue);
            if(domResult==0){
            meetingPartner = findMeetingPartner(meetPopulation);
                computeDominanceRanking(meetPopulation);
                int compResult = comparator.compare(this.getGenotype(), this.meetingPartner.getGenotype());
                if (compResult == -1) {
                    transferResourcesFrom(meetingPartner, transferResourceValue);
                } else if (compResult == 1) {
                    transferResourcesTo(meetingPartner, transferResourceValue);
                }
                if (compResult != 0) {
                    this.setMet(true);
                    meetingPartner.setMet(true);
                }
                return compResult;

            }
        return domResult;
    }

    @Override
    public String getAgentType() {
        return "JMetal5GlobalRankAgent";
    }
}
