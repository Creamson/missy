package pl.edu.agh.missy.convertion.aco2genetic;

import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.genetic.JMetalIntegerPermutationSolution;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.ant.Ant;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.collect.Lists.newArrayList;

public class CoinFlipGenotypeProvider implements GenotypeProvider {

    private PermutationProblem<PermutationSolution<Integer>> problem;
    private final ACO aco;
    private double acceptanceThreshold;
    private int lastId = 0;

    public CoinFlipGenotypeProvider(PermutationProblem<PermutationSolution<Integer>> problem, ExecutionStats stats, double acceptanceThreshold) {
        this.problem = problem;
        this.aco = stats.aco;
        this.acceptanceThreshold = acceptanceThreshold;
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

    private class CoinFlipAnt extends Ant {

        private CoinFlipAnt(ACO aco, int id) {
            super(aco, id);
        }

        @Override
        public void explore() {
            while (!nodesToVisit.isEmpty()) {

                int nextNode = -1;
                // Get the next node given the current node
                boolean nodeAccepted = false;
                List<Integer> nodesToVisitPrevious = newArrayList(nodesToVisit);
                while (!nodeAccepted && nodesToVisit.size() > 0) {
                    nextNode = aco.getAntExploration().getNextNode(this, currentNode); // This may still return same vertex multiple times
                    if (ThreadLocalRandom.current().nextDouble() >= CoinFlipGenotypeProvider.this.acceptanceThreshold) {
                        nodeAccepted = true;
                    } else {
                        nodesToVisit.remove(new Integer(nextNode));
                    }
                }

                // Remove the next node from the original list of nodes to visit
                nodesToVisit = nodesToVisitPrevious;
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
