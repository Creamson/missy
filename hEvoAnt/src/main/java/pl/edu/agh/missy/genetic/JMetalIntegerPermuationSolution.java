package pl.edu.agh.missy.genetic;

import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

import java.util.List;

public class JMetalIntegerPermuationSolution extends DefaultIntegerPermutationSolution {

    public JMetalIntegerPermuationSolution(PermutationProblem<?> problem, List<Integer> initialSequence) {
        super(problem);

        for (int i = 0; i < getNumberOfVariables(); i++) {
            setVariableValue(i, initialSequence.get(i)) ;
        }
    }
}
