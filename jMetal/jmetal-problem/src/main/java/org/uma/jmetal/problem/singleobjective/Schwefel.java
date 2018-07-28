package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class Schwefel extends AbstractDoubleProblem{
	private static final long serialVersionUID = 3609072466279604237L;

	public Schwefel(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(1);
	    setNumberOfConstraints(0) ;
	    setName("Schwefel");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-500.0);
	      upperLimit.add(500.0);
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
	    
	    double alpha = 4.18982887272434686131e+02;
	    double sum = 0.0;
        for (int i = 0; i < numberOfVariables; ++i) {
            sum -= x[i] * Math.sin(Math.sqrt(Math.abs(x[i])));
        }
        sum += numberOfVariables * alpha;
        solution.setObjective(0, sum);
	}
}
