package pl.edu.agh.missy.evo.emas;

import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5GlobalRankEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomInitializationGlobalRankEMAS extends JMetal5GlobalRankEMAS<PermutationSolution<Integer>> {

    private final BSFResultSaver resultSaver;
    private final GenotypeProvider initialGenotypeProvider;

    public CustomInitializationGlobalRankEMAS(Problem<PermutationSolution<Integer>> problem,
                                               BSFResultSaver resultSaver,
                                               CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                               MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                               MutationOperator<PermutationSolution<Integer>> strongMutationOperator,
                                               int maxNumberOfIterations,
                                               int numberOfIslands,
                                               int envEnergy,
                                               double initialAgentResourceLevel,
                                               double transferAgentResourceLevel,
                                               String algorithmName,
                                               int replaceOnlyIfBetter,
                                               EmasDominanceComparator comparator,
                                               EmasDominanceComparator parentToChildComparator) {
        this(null,
                problem,
                resultSaver,
                crossoverOperator,
                mutationOperator,
                strongMutationOperator,
                maxNumberOfIterations,
                numberOfIslands,
                envEnergy,
                initialAgentResourceLevel,
                transferAgentResourceLevel,
                algorithmName,
                replaceOnlyIfBetter,
                comparator,
                parentToChildComparator);
    }


    public CustomInitializationGlobalRankEMAS(GenotypeProvider initialGenotypeProvider,
                                               Problem<PermutationSolution<Integer>> problem,
                                               BSFResultSaver resultSaver,
                                               CrossoverOperator<PermutationSolution<Integer>> crossoverOperator,
                                               MutationOperator<PermutationSolution<Integer>> mutationOperator,
                                               MutationOperator<PermutationSolution<Integer>> strongMutationOperator,
                                               int maxNumberOfIterations,
                                               int numberOfIslands,
                                               int envEnergy,
                                               double initialAgentResourceLevel,
                                               double transferAgentResourceLevel,
                                               String algorithmName,
                                               int replaceOnlyIfBetter,
                                               EmasDominanceComparator comparator,
                                               EmasDominanceComparator parentToChildComparator) {
        super(problem,
                crossoverOperator,
                mutationOperator,
                strongMutationOperator,
                maxNumberOfIterations,
                numberOfIslands,
                envEnergy,
                initialAgentResourceLevel,
                transferAgentResourceLevel,
                algorithmName,
                replaceOnlyIfBetter,
                comparator,
                parentToChildComparator);
        this.resultSaver = resultSaver;
        this.initialGenotypeProvider = initialGenotypeProvider;
    }


    @Override
    protected void createInitialPopulation() {
        if(initialGenotypeProvider != null) {
            int numberOfAgents = (int) (envEnergy / initialAgentResourceLevel);

            population = Stream.generate(initialGenotypeProvider::nextGenotype)
                    .limit(numberOfAgents)
                    .map(genotype -> builder.withGenotype(genotype)
                            .withEMAS(this)
                            .withAgentType(getAgentType())
                            .withInitialResourcesValue(getInitialAgentResourceLevel())
                            .withCurrentIsland(0)
                            .withDominanceComparator(this.comparator)
                            .withParentToChildComparator(this.parentChildComparator)
                            .build())
                    .collect(Collectors.toList());
            population.forEach(agent -> agent.evaluate(getProblem()));
        } else {
            super.createInitialPopulation();
        }
    }

    @Override
    protected boolean isStoppingConditionReached() {
        resultSaver.recordCheckpoint(getResult().get(0).getObjective(0), "evo");
        return super.isStoppingConditionReached();
    }
}
