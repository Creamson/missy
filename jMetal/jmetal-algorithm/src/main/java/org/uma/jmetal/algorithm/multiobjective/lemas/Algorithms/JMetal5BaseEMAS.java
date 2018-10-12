package org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms;

import org.uma.jmetal.algorithm.impl.AbstractEMASAlgorithm;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5Agent;
import org.uma.jmetal.algorithm.multiobjective.lemas.Agents.JMetal5AgentBuilder;
import org.uma.jmetal.measure.Measurable;
import org.uma.jmetal.measure.MeasureManager;
import org.uma.jmetal.measure.impl.BasicMeasure;
import org.uma.jmetal.measure.impl.SimpleMeasureManager;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JMetal5BaseEMAS<S extends Solution<?>> extends AbstractEMASAlgorithm<S, List<S>> implements Measurable {

    protected List<JMetal5Agent<S>> population;

    public int iterations;

    public int evaluations;

    private String algorithmName="";

    private final int maxNumberOfIterations;

    private final int numberOfIslands;

    protected final int envEnergy;

    protected final double initialAgentResourceLevel;

    private final double transferAgentResourceLevel;

    private int replaceOnlyIfBetter = 0;

    private final Problem<S> problem;

    private final CrossoverOperator<S> crossoverOperator;

    private final MutationOperator<S> mutationOperator;

    private final MutationOperator<S> strongMutationOperator;

    protected final JMetal5AgentBuilder<S> builder = new JMetal5AgentBuilder<>();

    private SimpleMeasureManager measureManager = new SimpleMeasureManager();

    private BasicMeasure<Object> solutionListMeasure;

    protected EmasDominanceComparator comparator;

    protected EmasDominanceComparator parentChildComparator;

    private int imBetterMeetingTypeCounter;

    private int neitherIsBetterMeetingTypeCounter;


    public void setComparator(EmasDominanceComparator comparator) {
        this.comparator = comparator;
    }

    public void setReplaceOnlyIfBetter(int replaceOnlyIfBetter) {
        this.replaceOnlyIfBetter = replaceOnlyIfBetter;
    }

    public int getReplaceOnlyIfBetter() {
        return replaceOnlyIfBetter;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public double getInitialAgentResourceLevel() {
        return initialAgentResourceLevel;
    }

    public MutationOperator<S> getStrongMutationOperator() {

        return strongMutationOperator;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;

    }

    public int getImBetterMeetingTypeCounter() {
        return imBetterMeetingTypeCounter;
    }

    public int getNeitherIsBetterMeetingTypeCounter() {
        return neitherIsBetterMeetingTypeCounter;
    }


    /* Measures code */
    private void initMeasures() {
        solutionListMeasure = new BasicMeasure<>() ;
        measureManager.setPushMeasure("currentPopulation", solutionListMeasure);
    }



    public JMetal5BaseEMAS(Problem<S> problem, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                    MutationOperator<S> strongMutationOperator, int maxNumberOfIterations, int numberOfIslands,
                    int envEnergy, double initialAgentResourceLevel, double transferAgentResourceLevel,String algorithmName,int replaceOnlyIfBetter, EmasDominanceComparator comparator, EmasDominanceComparator parentChildComparator) {

                    this.problem = problem;

                    this.comparator = comparator;

                    this.parentChildComparator = parentChildComparator;

                    this.crossoverOperator = crossoverOperator;

                    this.mutationOperator = mutationOperator;

                    this.numberOfIslands = numberOfIslands;

                    this.envEnergy = envEnergy;

                    this.initialAgentResourceLevel = initialAgentResourceLevel;

                    this.transferAgentResourceLevel = transferAgentResourceLevel;

                    this.strongMutationOperator = strongMutationOperator;

                    this.maxNumberOfIterations = maxNumberOfIterations;

                    this.replaceOnlyIfBetter = replaceOnlyIfBetter;

                    this.setName(algorithmName);
//        builder
//                .withAgentType(getAgentType())
//                .withInitialResourcesValue(initialAgentResourceLevel)
//                .withCurrentIsland(0);
        initMeasures();
    }

    @Override
    protected void createInitialPopulation() {
        int numberOfAgents = (int) (envEnergy / initialAgentResourceLevel);

        population = Stream.generate(problem::createSolution)
                .limit(numberOfAgents)
                .map(genotype -> builder.withGenotype(genotype)
                        .withEMAS(this)
                        .withAgentType(getAgentType())
                        .withInitialResourcesValue(initialAgentResourceLevel)
                        .withCurrentIsland(0)
                        .withDominanceComparator(this.comparator)
                        .withParentToChildComparator(this.parentChildComparator)
                        .build())
//                .map(genotype -> new JMetal5Agent<>(genotype,
//                        crossoverOperator,initialAgentResourceLevel,1,comparator,this))
                .collect(Collectors.toList());
        population.forEach(agent -> agent.evaluate(problem));
    }

    protected void populationLOG(String preamble){
        if(Constants.LOG_LEVEL==2){
            System.out.println(preamble);
            for (JMetal5Agent a: population) {
                System.out.println(a.toString()+ " " + a.genotype.getObjective(0) + " " + a.genotype.getObjective(1) + " " + a.getResourceLevel());
            }
        }
    }

    @Override
    protected void meetStep() {
        populationLOG("======meetStep=====");
        resetMeetingStatistics();
        List<JMetal5Agent<S>> meetingAgents = population;

        for (JMetal5Agent a : meetingAgents) {
            a.setMet(false);
        }

        for (JMetal5Agent<S> agent : meetingAgents) {
            if (!agent.isMet()) {
                int meetingResult = agent.doMeeting(meetingAgents, transferAgentResourceLevel);
                updateMeetingStatistics(meetingResult);
            }
        }

    }

    private void resetMeetingStatistics() {
        this.imBetterMeetingTypeCounter = 0;
        this.neitherIsBetterMeetingTypeCounter = 0;
    }

    private void updateMeetingStatistics(int meetingResult) {
        if (meetingResult == 0)
            neitherIsBetterMeetingTypeCounter++;
        else
            imBetterMeetingTypeCounter++;
    }

    public void updateEvaluationsCounter(){
        this.evaluations++;
//        System.out.println(this + "  "+ evaluations);
    }

    @Override
    protected void reproStep() {
        populationLOG("----reproStep----");
        List<JMetal5Agent<S>> partners;
        List<JMetal5Agent<S>> reproduced = new ArrayList<>();

        population.stream().forEach(a->a.hasAlreadyReproduced=false);

        while ((partners = population.stream().filter(a-> a.canReproduce() && !a.hasAlreadyReproduced).collect(Collectors.toList())).size() > 0) {
            reproduced.addAll(partners.get(0).doReproduce(partners, problem, crossoverOperator,
                    mutationOperator, strongMutationOperator, initialAgentResourceLevel,this));
        }
        population.addAll(reproduced);
    }


    @Override
    protected void deadStep() {
        population = population.stream().filter(JMetal5Agent::isAlive).collect(Collectors.toList());
    }

    @Override
    protected void initProgress() {
        iterations = 0;
        evaluations = 0;
    }

    @Override
    protected void updateProgress() {
        iterations++;
        solutionListMeasure.push(getPopulation());
    }

    @Override
    protected void updateProgress(int iteration) {
        iterations++;
        solutionListMeasure.push(getPopulation());
    }


    @Override
    protected boolean isStoppingConditionReached() {
        return iterations >= maxNumberOfIterations;
//        return evaluations >= Constants.MAX_EVALUATIONS;
    }

    @Override
    public Problem<S> getProblem() {
        return problem;
    }

    public String getAgentType() {
        return "JMetal5BaseAgent";
    }

    @Override
    public List<S> getPopulation() {
        return population.stream().map(JMetal5Agent::getGenotype).collect(Collectors.toList());
    }

    @Override
    public int getIterations() {
        return iterations;
    }

    @Override
    public List<S> getResult() {
        return getNonDominatedSolutions(getPopulation());
    }

    private List<S> getNonDominatedSolutions(List<S> solutionList) {
        return SolutionListUtils.getNondominatedSolutions(solutionList);
    }

    @Override
    public String getName() {
        return this.algorithmName;
    }

    public void setName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String getDescription() {
        return "JMetal5BaseEMAS";
    }

    @Override
    public MeasureManager getMeasureManager() {
        return measureManager;
    }
}
