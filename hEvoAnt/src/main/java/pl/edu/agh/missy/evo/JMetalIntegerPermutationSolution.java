package pl.edu.agh.missy.evo;

import org.uma.jmetal.problem.PermutationProblem;
import org.uma.jmetal.solution.impl.DefaultIntegerPermutationSolution;

import java.util.List;

public class JMetalIntegerPermutationSolution extends DefaultIntegerPermutationSolution {

    public JMetalIntegerPermutationSolution(PermutationProblem<?> problem, List<Integer> initialSequence) {
        super(problem);

        for (int i = 0; i < getNumberOfVariables(); i++) {
            setVariableValue(i, initialSequence.get(i)) ;
        }
    }
}
