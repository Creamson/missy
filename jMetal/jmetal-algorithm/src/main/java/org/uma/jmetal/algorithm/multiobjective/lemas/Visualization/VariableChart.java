package org.uma.jmetal.algorithm.multiobjective.lemas.Visualization;

import org.knowm.xchart.XYSeries;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.lemas.Algorithms.JMetal5BaseEMAS;
import org.uma.jmetal.algorithm.multiobjective.lemas.Utils.Constants;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.List;
import java.util.stream.Collectors;

public class VariableChart extends BaseChart {
    private int indexOFVariableToShow = 0;

    public VariableChart(List<Algorithm> algorithmToShow, int indexOFVariableToShow) {
        super();
        this.chart.getStyler().setLegendVisible(false);
        this.indexOFVariableToShow = indexOFVariableToShow;
        this.getChart().setTitle("X" + indexOFVariableToShow);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setXAxisMax(Constants.PROBLEM.getProblem().createSolution().getUpperBound(indexOFVariableToShow));
        chart.getStyler().setXAxisMin(Constants.PROBLEM.getProblem().createSolution().getLowerBound(indexOFVariableToShow));
        for (Algorithm alg : algorithmToShow) {
            chart.addSeries(alg.getName(), new double[]{0}, new double[]{0});
        }
    }

    @Override
    public void update(List<DoubleSolution> population) {
        chart.updateXYSeries(
                "variable",
                population.stream().map(solution -> solution.getObjective(0)).collect(Collectors.toList()),
                population.stream().map(solution -> solution.getObjective(1)).collect(Collectors.toList()),
                null);
    }

    @Override
    public void update(List<DoubleSolution> population, String seriesName) {
        chart.updateXYSeries(
                seriesName,
                population.stream().map(solution -> solution.getVariableValue(0)).collect(Collectors.toList()),

                population.stream().map(solution -> solution.getObjective(1)).collect(Collectors.toList()),
                null);
    }

    @Override
    public void update(List<DoubleSolution> population, String seriesName, JMetal5BaseEMAS emas) {
        if (emas.getIterations() < Constants.MAX_ITERATIONS) {
            chart.updateXYSeries(
                    seriesName,
                    population.stream().map(solution -> solution.getVariableValue(indexOFVariableToShow)).collect(Collectors.toList()),
                    population.stream().map(solution -> emas.getIterations() + 10 * indexOFVariableToShow).collect(Collectors.toList()),
                    null);
        }
    }




    @Override
    public void update(List<DoubleSolution> population, String seriesName, JMetal5BaseEMAS emas, int seriesNumber) {
        if (emas.getIterations() < Constants.MAX_ITERATIONS) {
            chart.updateXYSeries(
                    seriesName,
                    population.stream().map(solution -> solution.getVariableValue(indexOFVariableToShow)).collect(Collectors.toList()),
                    population.stream().map(solution -> seriesNumber * 10).collect(Collectors.toList()),
                    null);
        }
    }

    @Override
    public void update(List<DoubleSolution> population, String seriesName, Algorithm emas, int seriesNumber) {
//        if (emas.getIterations() < Constants.MAX_ITERATIONS) {
            chart.updateXYSeries(
                    seriesName,
                    population.stream().map(solution -> solution.getVariableValue(indexOFVariableToShow)).collect(Collectors.toList()),
                    population.stream().map(solution -> seriesNumber * 10).collect(Collectors.toList()),
                    null);
//        }
    }
}
