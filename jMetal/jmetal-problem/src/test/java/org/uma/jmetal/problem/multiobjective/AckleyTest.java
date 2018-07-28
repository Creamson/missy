package org.uma.jmetal.problem.multiobjective;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Ackley;
import org.uma.jmetal.solution.DoubleSolution;

public class AckleyTest {
	private static final double PRECISSION = 0.0001;
	Problem<DoubleSolution> problem;
	
	@Test
	public void evaluateSimpleSolutions() {
		problem = new Ackley(1);
		DoubleSolution solution = problem.createSolution();
		assertThat(solution.getVariableValue(0), allOf(greaterThan(-32.768), lessThan(32.768)));
		
		solution.setVariableValue(0, 0.0);
		problem.evaluate(solution);
		assertEquals(0.0, (double)solution.getObjective(0), PRECISSION);
		
		solution.setVariableValue(0, 5.0);
		problem.evaluate(solution);
		assertEquals(12.6424, (double)solution.getObjective(0), PRECISSION);
		
		solution.setVariableValue(0, Math.PI);
		problem.evaluate(solution);
		assertEquals(10.1715, (double)solution.getObjective(0), PRECISSION);
	}
	
	@Test
	public void evaluateSolutions() {
		problem = new Ackley(3);
		DoubleSolution solution = problem.createSolution();
		assertThat(solution.getVariableValue(0), allOf(greaterThan(-32.768), lessThan(32.768)));
		assertThat(solution.getVariableValue(1), allOf(greaterThan(-32.768), lessThan(32.768)));
		assertThat(solution.getVariableValue(2), allOf(greaterThan(-32.768), lessThan(32.768)));
		
		solution.setVariableValue(0, 0.0);
		solution.setVariableValue(1, 0.0);
		solution.setVariableValue(2, 0.0);
		problem.evaluate(solution);
		assertEquals(0.0, (double)solution.getObjective(0), PRECISSION);
		
		solution.setVariableValue(0, 1.0);
		solution.setVariableValue(1, 2.0);
		solution.setVariableValue(2, 3.0);
		problem.evaluate(solution);
		assertEquals(7.0164, (double)solution.getObjective(0), PRECISSION);
	}
}
