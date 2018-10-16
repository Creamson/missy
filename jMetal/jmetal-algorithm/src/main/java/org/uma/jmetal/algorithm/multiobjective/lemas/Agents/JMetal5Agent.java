package org.uma.jmetal.algorithm.multiobjective.lemas.Agents;

import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5BaseEMAS;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.abs;


public class JMetal5Agent<S extends Solution<?>> {

    private static Random rand = new Random();

    public boolean hasAlreadyReproduced = false;

    public S genotype;

    public CrossoverOperator crossover;

    JMetal5Agent<S> meetingPartner;

    private boolean met = false;

    private double resourceLevel = 0;

    private int currentIsland;

    private EmasDominanceComparator comparator;

    private EmasDominanceComparator parentToChildComparator;

    private JMetal5BaseEMAS EMAS;

    private List<Double> lowestKnownObjectiveValues = new ArrayList<>();

    private List<Double> highestKnownObjectiveValues = new ArrayList<>();

    private List<List<Double>> knownNonDominatedObjectiveValues = new ArrayList<>(Constants.PROBLEM.getProblem().getNumberOfObjectives());

    double distanceToClosestNeighbour = Double.POSITIVE_INFINITY;

    //private List<List<Double>> distanceToClosestNeighbour = new ArrayList<>();

//    public JMetal5Agent(S genotype, CrossoverOperator crossover, double resourceLevel, int currentIsland, EmasDominanceComparator comparator, JMetal5BaseEMAS EMAS) {
//        this.genotype = genotype;
//        this.crossover = crossover;
//        this.resourceLevel = resourceLevel;
//        this.currentIsland = currentIsland;
//        this.comparator = comparator;
//        this.EMAS = EMAS;
//    }

    public List<List<Double>> getKnownNonDominatedObjectiveValues() {
        return knownNonDominatedObjectiveValues;
    }


    public JMetal5BaseEMAS getEMAS() {
        return EMAS;
    }

    public void setEMAS(JMetal5BaseEMAS EMAS) {
        this.EMAS = EMAS;
    }

    public void setHasAlreadyReproduced(boolean hasAlreadyReproduced) {
        this.hasAlreadyReproduced = hasAlreadyReproduced;
    }


    public double getResourceLevel() {
        return resourceLevel;
    }

    void setResourceLevel(double resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public void setCrossover(CrossoverOperator crossover) {
        this.crossover = crossover;
    }

    public String getAgentType() {
        return "JMetal5BaseAgent";
    }

    public boolean isMet() {
        return met;
    }

    public void setMet(boolean met) {
        this.met = met;
    }

    private void loseResources(double delta) {
        this.resourceLevel -= delta;
    }

    private void gainResources(double delta) {
        this.resourceLevel += delta;
    }

    public S getGenotype() {
        return genotype;
    }

    void setGenotype(S genotype) {
        this.genotype = genotype;
    }

    int getCurrentIsland() {
        return currentIsland;
    }

    void setCurrentIsland(int island) {
        currentIsland = island;
    }

    public void setComparator(EmasDominanceComparator comparator) {
        this.comparator = comparator;
    }

    public void setParentToChildComparator(EmasDominanceComparator parentToChildComparator) {
        this.parentToChildComparator = parentToChildComparator;
    }

    public void mutate(MutationOperator<S> mutationOperator) {
        mutationOperator.execute(genotype);
    }

    public void transferResourcesTo(JMetal5Agent<S> agent, double resourceDelta) {
        agent.gainResources(resourceDelta);
        loseResources(resourceDelta);
    }

    public void transferResourcesFrom(JMetal5Agent agent, double resourceDelta) {
        agent.loseResources(resourceDelta);
        gainResources(resourceDelta);
    }


    public void updateKnownNonDominatedObjectives(JMetal5Agent<S> agent2) {
        List<Double> objectiveListToAddToAgent1 = new ArrayList<>();
        List<Double> objectiveListToAddToAgent2 = new ArrayList<>();
        for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
            objectiveListToAddToAgent1.add(agent2.genotype.getObjective(i));
            objectiveListToAddToAgent2.add(this.genotype.getObjective(i));
        }
        if (!knownNonDominatedObjectiveValues.contains(objectiveListToAddToAgent1)) {
            knownNonDominatedObjectiveValues.add(objectiveListToAddToAgent1);
        }
        if (!agent2.knownNonDominatedObjectiveValues.contains(objectiveListToAddToAgent2)) {
            agent2.knownNonDominatedObjectiveValues.add(objectiveListToAddToAgent2);
        }
    }

    int checkIfBetter(JMetal5Agent<S> agent1, JMetal5Agent<S> agent2) {
        return comparator.compare(agent1, agent2);
    }


    private void updateAnchorList(JMetal5Agent partner) {
        if (this.lowestKnownObjectiveValues.isEmpty()) {
            for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
                this.lowestKnownObjectiveValues.add(this.genotype.getObjective(i));
            }
        }

        if (partner.lowestKnownObjectiveValues.isEmpty()) {
            for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
                partner.lowestKnownObjectiveValues.add(partner.genotype.getObjective(i));
            }
        }

        if (this.highestKnownObjectiveValues.isEmpty()) {
            for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
                this.highestKnownObjectiveValues.add(this.genotype.getObjective(i));
            }
        }

        if (partner.highestKnownObjectiveValues.isEmpty()) {
            for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
                partner.highestKnownObjectiveValues.add(partner.genotype.getObjective(i));
            }
        }


        for (int i = 0; i < EMAS.getProblem().getNumberOfObjectives(); i++) {
            this.lowestKnownObjectiveValues.set(i, Math.min(this.lowestKnownObjectiveValues.get(i), (Double) partner.lowestKnownObjectiveValues.get(i)));
            this.highestKnownObjectiveValues.set(i, Math.min(this.highestKnownObjectiveValues.get(i), (Double) partner.highestKnownObjectiveValues.get(i)));
        }
    }

    public double getDistanceToClosestNeighbour() {
        return distanceToClosestNeighbour;
    }

    public void updateDistanceToClosestNeighbour(JMetal5Agent meetingPartner){
        int numberOfObjectives = meetingPartner.genotype.getNumberOfObjectives();

        double distance = 0;
        for(int i=0;i<numberOfObjectives;i++){
            distance += abs(this.getGenotype().getObjective(i) - meetingPartner.getGenotype().getObjective(i));
        }
        if(distance < this.distanceToClosestNeighbour){
            this.distanceToClosestNeighbour = distance;
        }

        if(distance<meetingPartner.distanceToClosestNeighbour){
            meetingPartner.distanceToClosestNeighbour = distance;
        }
    }


    public int doMeeting(List<? extends JMetal5Agent<S>> meetPopulation, double transferResourceValue) {
        meetingPartner = findMeetingPartner(meetPopulation);
        updateDistanceToClosestNeighbour(meetingPartner);

        int comparatorResult = checkIfBetter(this, meetingPartner);
        if (comparatorResult == -1) {
            transferResourcesFrom(meetingPartner, transferResourceValue);
        } else if (comparatorResult == 1) {
            transferResourcesTo(meetingPartner, transferResourceValue);
        }

        if (comparatorResult != 0) {
            setMet(true);
            meetingPartner.setMet(true);
        }
        updateAnchorList(meetingPartner);

        return comparatorResult;
    }

    private void meetingLog(String preamble, JMetal5Agent agent1, JMetal5Agent agent2, int dominance, int isBetter) {
        if (Constants.LOG_LEVEL == 2) {
            System.out.println(preamble);
            System.out.println(agent1.genotype.getObjective(0) + " " + agent1.genotype.getObjective(1) + " " + agent1.getResourceLevel());
            System.out.println(agent2.genotype.getObjective(0) + " " + agent2.genotype.getObjective(1) + " " + agent2.getResourceLevel());
            System.out.println("dominance result: " + dominance + ". Extended dominance result: " + isBetter);
        }
    }

    public List<? extends JMetal5Agent<S>> doReproduce(List<? extends JMetal5Agent<S>> matingPopulation, Problem<S> problem,
                                                       CrossoverOperator<S> crossoverOperator,
                                                       MutationOperator<S> mutationOperator,
                                                       MutationOperator<S> strongMutationOperator,
                                                       double initialAgentResourceLevel,
                                                       JMetal5BaseEMAS EMAS) {

        JMetal5AgentBuilder<S> builder = new JMetal5AgentBuilder<>();
        JMetal5Agent<S> matingPartner = findMatingPartner(matingPopulation);

        List<? extends JMetal5Agent<S>> parentList = Arrays.asList(this, matingPartner);

        List<S> off = (List<S>) crossoverOperator.execute(
                parentList.stream().map(a -> a.genotype).collect(Collectors.toList()));


        List<? extends JMetal5Agent<S>> offspringList = off.stream().map(genotype ->
                builder
                    .withGenotype(genotype)
                    .withAgentType(getAgentType())
                    .withEMAS(EMAS)
                    .withDominanceComparator(comparator)
                        .withParentToChildComparator(parentToChildComparator)
                    .withAgentType(getAgentType())
//                    .withInitialResourcesValue(initialAgentResourceLevel)
                    .withCurrentIsland(0)
                .build())
                .collect(Collectors.toList());

//        List<JMetal5Agent<S>> offspringList = off.stream().map(genotype ->
//                new JMetal5Agent<>(genotype,
//                        crossoverOperator,0,
//                        1,comparator, EMAS))
//                .collect(Collectors.toList());


        return (this.equals(matingPartner))
                ? reproAct(strongMutationOperator, 1, problem, parentList, offspringList)
                : reproAct(mutationOperator, 2, problem, parentList, offspringList);
    }


    public List<JMetal5Agent<S>> reproAct(MutationOperator<S> mutationOperator, int numberOfChildren,
                                          Problem<S> problem, List<? extends JMetal5Agent<S>> parentList,
                                          List<? extends JMetal5Agent<S>> offspringList) {
        List<JMetal5Agent<S>> offspringToBeReturned = new ArrayList<>();
        for (int index = 0; index < numberOfChildren; index++) {
            JMetal5Agent<S> agent = offspringList.get(index);
            agent.mutate(mutationOperator);
            agent.evaluate(problem);
            int isOffspringBetter = 0;
            if (EMAS.getReplaceOnlyIfBetter() != 0) {
                isOffspringBetter = getIsOffspringBetter(parentList.get(index), agent);
            }

            if ((EMAS.getReplaceOnlyIfBetter() == 0) || (EMAS.getReplaceOnlyIfBetter() == 1 && isOffspringBetter == 1) || (EMAS.getReplaceOnlyIfBetter()==2 && isOffspringBetter !=-1)) {
                agent.transferResourcesFrom(parentList.get(index), Constants.INITIAL_RESOURCE_VALUE);
                offspringToBeReturned.add(agent);
            }
            parentList.get(index).hasAlreadyReproduced = true;

        }
        return offspringToBeReturned;
    }

    private int getIsOffspringBetter(JMetal5Agent<S> parent, JMetal5Agent<S> agent) {
        return parentToChildComparator.compare(parent, agent);
//        return this.checkIfBetter(parent, agent);
    }


    public void evaluate(Problem<S> problem) {
        problem.evaluate(getGenotype());
        EMAS.updateEvaluationsCounter();
    }

    public JMetal5Agent<S> findMatingPartner(List<? extends JMetal5Agent<S>> matingPopulation) {

        if (matingPopulation.size() == 1)
            return this;
        else {
            List<JMetal5Agent<S>> filtered = matingPopulation.stream().filter(a -> a.isAlive() && !a.equals(this)).collect(Collectors.toList());
            if (filtered.size() == 0) return this;
            return filtered.get(rand.nextInt(filtered.size()));
        }
    }


    public JMetal5Agent<S> findMeetingPartner(List<? extends JMetal5Agent<S>> meetPopulation) {
        List<JMetal5Agent<S>> meetingPartners = meetPopulation.stream().filter(a ->
                a.getCurrentIsland() == getCurrentIsland()
                        && !a.isMet()
                        && !a.equals(this))
                .collect(Collectors.toList());

        return (meetingPartners.size() == 0)
                ? this
                : meetingPartners.get(rand.nextInt(meetingPartners.size()));
    }

    public boolean isAlive() {
        return resourceLevel > Constants.DEATH_LEVEL_VALUE;
    }

    public boolean canReproduce() {
        return resourceLevel >= Constants.REPRODUCTION_LEVEL_VALUE;
    }
}

