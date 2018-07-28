package org.uma.jmetal.algorithm.multiobjective.lemas.Utils;


//import javafx.scene.paint.Color;

import org.knowm.xchart.style.Styler;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.multiobjective.zdt.*;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

public class Constants {
    public static int MATURITY_AGE = 2;
    public static int LOG_LEVEL = 0;
    public static final double REPRODUCTION_LEVEL_VALUE = 2;
    public static final double PROGRESSIVE_REPRODUCTION_LEVEL_VALUE = 1;
    public static final double INITIAL_RESOURCE_VALUE = 1;
    public static final double DEATH_LEVEL_VALUE = 0;
    public static final double TRANSFER_RESOURCE_VALUE = 1.0;
    public static final int ENV_ENERGY = 170;
    public static final Styler.ChartTheme CHART_THEME = Styler.ChartTheme.Matlab;
    public static final int HV_FREQUENCY = 50;
    public static final int POP_FREQUENCY = 1;
    public static final int MEETINGS_FREQUENCY = 50;
    public static final CrossoverOperator<DoubleSolution> XOP = new SBXCrossover(1.0, 5.0);
    public static final ExperimentProblem<DoubleSolution> PROBLEM = new ExperimentProblem<>(new ZDT1());
    public static final ExperimentProblem<DoubleSolution> PROBLEM_ZDT2 = new ExperimentProblem<>(new ZDT2());
    public static final ExperimentProblem<DoubleSolution> PROBLEM_ZDT3 = new ExperimentProblem<>(new ZDT3());
    public static final ExperimentProblem<DoubleSolution> PROBLEM_ZDT4 = new ExperimentProblem<>(new ZDT4());

    public static final int NUMBER_OF_DECISION_VARIABLES_TO_SHOW = 3;
    public static final MutationOperator<DoubleSolution> MOP =
            new PolynomialMutation(1.0 / PROBLEM.getProblem().getNumberOfVariables(),
                    10.0);
    public static final MutationOperator<DoubleSolution> STRONG_MOP =
            new PolynomialMutation(1.0,
                    20.0);
    static final SelectionOperator SEL_OP = new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());

    public static int MAX_ITERATIONS = 1000;
    public static int MAX_EVALUATIONS = 2500;
    public static String REF_FRONT_DIR = "../jMetal/jmetal-problem/src/test/resources/pareto_fronts/";
    public static int NUMBER_OF_ISLANDS = 1;
    public static double NOISE_LEVEL = 0.0;
    public static double[][] ALPHA_VALUES = {{0.0, 0,1},{0.1, 0.0}};
//    public static Color REFERENCE_COLOR = Color.GREEN;
    //    public static boolean WITH_VISUALIZATION = true;
}