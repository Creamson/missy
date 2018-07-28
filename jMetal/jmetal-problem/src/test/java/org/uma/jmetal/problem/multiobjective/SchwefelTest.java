package org.uma.jmetal.problem.multiobjective;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import org.junit.Test;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.singleobjective.Schwefel;
import org.uma.jmetal.solution.DoubleSolution;

public class SchwefelTest {
	private static final double PRECISSION = 0.0001;
	Problem<DoubleSolution> problem;

	@Test
	public void evaluateSimpleSolutions() {
		problem = new Schwefel(1);
		DoubleSolution solution = problem.createSolution();
		
		assertThat(solution.getVariableValue(0), allOf(greaterThan(-500.0), lessThan(500.0)));

		solution.setVariableValue(0, 420.968746);
		problem.evaluate(solution);
		assertEquals(0.0, solution.getObjective(0), PRECISSION);

		solution.setVariableValue(0, 5.0);
		problem.evaluate(solution);
		assertEquals(415.0491413422639, (double) solution.getObjective(0), PRECISSION);

		solution.setVariableValue(0, Math.PI);
		problem.evaluate(solution);
		assertEquals(415.904955792076, (double) solution.getObjective(0), PRECISSION);
	}

	@Test
	public void evaluateSolutions() {
		problem = new Schwefel(3);
		DoubleSolution solution = problem.createSolution();
		assertThat(solution.getVariableValue(0), allOf(greaterThan(-500.0), lessThan(500.0)));
		assertThat(solution.getVariableValue(1), allOf(greaterThan(-500.0), lessThan(500.0)));
		assertThat(solution.getVariableValue(2), allOf(greaterThan(-500.0), lessThan(500.0)));

		solution.setVariableValue(0, 420.968746);
		solution.setVariableValue(1, 420.968746);
		solution.setVariableValue(2, 420.968746);
		problem.evaluate(solution);
		assertEquals(0.0, (double) solution.getObjective(0), PRECISSION);

		solution.setVariableValue(0, 1.0);
		solution.setVariableValue(1, 2.0);
		solution.setVariableValue(2, 3.0);
		problem.evaluate(solution);
		assertEquals(1251.1705781882354, (double) solution.getObjective(0), PRECISSION);
	}
}
