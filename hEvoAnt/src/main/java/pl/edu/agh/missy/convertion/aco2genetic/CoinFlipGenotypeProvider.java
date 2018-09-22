package pl.edu.agh.missy.convertion.aco2genetic;

import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.genetic.JMetalIntegerPermutationSolution;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CoinFlipGenotypeProvider implements GenotypeProvider {

    private PermutationProblem<PermutationSolution<Integer>> problem;
    private final ACO aco;
    private int lastId = 0;

    public CoinFlipGenotypeProvider(PermutationProblem<PermutationSolution<Integer>> problem, ExecutionStats stats) {
        this.problem = problem;
        this.aco = stats.aco;

    }

    @Override
    public PermutationSolution<Integer> nextGenotype() {
        Ant nextAnt = new CoinFlipAnt(aco, (lastId++) % aco.getNumberOfAnts());
        nextAnt.setAntInitialization(aco.getAntInitialization());
        nextAnt.run();
        return new JMetalIntegerPermutationSolution(
                problem,
                nextAnt.tour
        );
    }

    private static class CoinFlipAnt extends Ant {

        public CoinFlipAnt(ACO aco, int id) {
            super(aco, id);
        }

        @Override
        public void explore() {
            while (!nodesToVisit.isEmpty()) {

                int nextNode = -1;
                Set<Integer> rejectedNodes = new HashSet<>();
                // Get the next node given the current node
                boolean nodeAccepted = false;
                while (!nodeAccepted && rejectedNodes.size() < nodesToVisit.size()) {
                    nextNode = aco.getAntExploration().getNextNode(this, currentNode);
                    if (!rejectedNodes.contains(nextNode) && ThreadLocalRandom.current().nextBoolean()) {
                        nodeAccepted = true;
                    } else {
                        rejectedNodes.add(nextNode);
                    }
                }

                // Remove the next node from the list of nodes to visit
                nodesToVisit.remove(new Integer(nextNode));

                // Save the next node in the tour
                tour.add(nextNode);

                // Mark as visited the arc(i,j)
                path[currentNode][nextNode] = 1;
                path[nextNode][currentNode] = 1;

                // update the list of the nodes to visit
                nodesToVisit = aco.getProblem().updateNodesToVisit(tour, nodesToVisit);

                // Define the next node as current node
                currentNode = nextNode;
            }
        }
    }
}
