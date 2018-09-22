package pl.edu.agh.missy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.uma.jmetal.algorithm.impl.AbstractGeneticAlgorithm;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.HybridRunner;
import pl.edu.agh.missy.results.BSFResultSaver;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.List;
import java.util.function.Function;

@Configuration
//@PropertySource("classpath:application.properties")
@Import({
        AcoConfiguration.class,
        GeneticConfiguration.class
})
public class HybridConfiguration {

    @Value("${general.numberOfIterations:5}")
    private int numberOfIterations;

    @Bean
    public HybridRunner runner(Function<List<PermutationSolution<Integer>>, ACO> acoSystemFactory,
                               Function<ExecutionStats, AbstractGeneticAlgorithm<PermutationSolution<Integer>, PermutationSolution<Integer>>> geneticAlgorithmFactory,
                               BSFResultSaver resultSaver) {
        return new HybridRunner(acoSystemFactory, geneticAlgorithmFactory, numberOfIterations, resultSaver);
    }
}