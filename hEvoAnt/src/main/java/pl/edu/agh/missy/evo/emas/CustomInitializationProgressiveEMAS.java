package pl.edu.agh.missy.evo.emas;

import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5ProgressiveEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Comparators.EmasDominanceComparator;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.convertion.aco2genetic.GenotypeProvider;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomInitializationProgressiveEMAS extends JMetal5ProgressiveEMAS<PermutationSolution<Integer>> {

    private final BSFResultSaver resultSaver;
    private final GenotypeProvider initialGenotypeProvider;

    public CustomInitializationProgressiveEMAS(Problem problem,
                                               BSFResultSaver resultSaver,
                                               CrossoverOperator crossoverOperator,
                                               MutationOperator mutationOperator,
                                               MutationOperator strongMutationOperator,
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


    public CustomInitializationProgressiveEMAS(GenotypeProvider initialGenotypeProvider,
                                               Problem problem,
                                               BSFResultSaver resultSaver,
                                               CrossoverOperator crossoverOperator,
                                               MutationOperator mutationOperator,
                                               MutationOperator strongMutationOperator,
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

        int numberOfAgents = (int) (envEnergy / initialAgentResourceLevel);

//        population = Stream.generate(() -> getProblem().createSolution())
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
//                .map(genotype -> new JMetal5Agent<>(genotype,
//                        crossoverOperator,initialAgentResourceLevel,1,comparator,this))
                .collect(Collectors.toList());
        population.forEach(agent -> agent.evaluate(getProblem()));
        
    }

    @Override
    protected List<PermutationSolution<Integer>> selection(List<PermutationSolution<Integer>> population){
        resultSaver.recordCheckpoint(getResult().getObjective(0), "evo");
        return super.selection(population);
    }
}
