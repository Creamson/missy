package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.problem.singleobjective.cec2005competitioncode.Benchmark;
import org.uma.jmetal.solution.DoubleSolution;
/**
 * Representation of the Ackley function.
 * 
 * @author Mateusz Godzik
 */
public class Ackley extends AbstractDoubleProblem {
	private static final long serialVersionUID = 3174689560950503880L;

	public Ackley(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(1);
	    setNumberOfConstraints(0) ;
	    setName("Ackley");
	    
	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-32.768);
	      upperLimit.add(32.768);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}
	
	@Override
	public void evaluate(DoubleSolution solution) {
		int numberOfVariables = getNumberOfVariables() ;

	    double[] x = new double[numberOfVariables] ;

	    for (int i = 0; i < numberOfVariables; i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
		
		double result = Benchmark.ackley(x);
		solution.setObjective(0, result );
	}

}
