package pl.edu.agh.missy.evo.emas;

import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5BaseEMAS;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.evo.JMetalEvolutionaryAlgorithm;

import java.util.List;

public class EMASWrapper implements JMetalEvolutionaryAlgorithm {

    private final JMetal5BaseEMAS<PermutationSolution<Integer>> emas;

    public EMASWrapper(JMetal5BaseEMAS<PermutationSolution<Integer>> emas) {
        this.emas = emas;
    }

    @Override
    public List<PermutationSolution<Integer>> getPopulation() {
        return emas.getPopulation();
    }

    @Override
    public void run() {
        emas.run();
    }

    @Override
    public PermutationSolution<Integer> getResult() {
        return emas.getResult().get(0);
    }

    @Override
    public String getName() {
        return emas.getName();
    }

    @Override
    public String getDescription() {
        return emas.getDescription();
    }
}
