package pl.edu.agh.missy.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.uma.jmetal.solution.PermutationSolution;
import pl.edu.agh.missy.HybridRunner;
import pl.edu.agh.missy.ants.*;
import pl.edu.agh.missy.convertion.genetic2aco.PheromonesInitializer;
import pl.edu.agh.missy.convertion.genetic2aco.SingleDepositionPerGenotypeInitializer;
import pl.edu.agh.missy.results.BSFResultSaver;
import pl.edu.agh.missy.results.SaveResultDaemon;
import thiagodnf.jacof.aco.ACO;
import thiagodnf.jacof.aco.AntSystem;
import thiagodnf.jacof.aco.daemonactions.AbstractDaemonActions;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.problem.tsp.TravellingSalesmanProblem;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
@Import({
        ResultSavingConfiguration.class
})
public class AcoConfiguration {

    private static final Map<String, BiFunction<Optional<PheromonesInitializer>, Problem, ACO>> antSystems = ImmutableMap.of(
            "antColonySystem", CustomInitializationAntColonySystem::new,
            "antSystem", CustomInitializationAntSystem::new,
            "elitistAntSystem", CustomInitializationElitistAntSystem::new,
            "maxMinAntSystem", CustomInitializationMaxMinAntSystem::new,
            "rankBasedAntSystem", CustomInitializationRankBasedAntSystem::new
    );

    @Value("${aco.systemName}")
    private String systemName;
    @Value("${aco.problemPath}")
    private String problemPath;
    @Value("${aco.depositionRate:1.0}")
    private double depositionRate;
    @Value("${aco.numberOfAnts:10}")
    private int numberOfAnts;
    @Value("${aco.numberOfIterations:20}")
    private int numberOfIterations;
    @Value("${aco.alpha:1.0}")
    private double alpha;
    @Value("${aco.beta:2.0}")
    private double beta;
    @Value("${aco.rho:0.1}")
    private double rho;


    private TravellingSalesmanProblem getAcoProblem() {
        try {
            String acoProblemPath = this.problemPath;
            return new TravellingSalesmanProblem(acoProblemPath);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ACO getAntSystem(BSFResultSaver bsfResultSaver, List<PermutationSolution<Integer>> population) {
        Problem problem = getAcoProblem();

        ACO aco = antSystems.get(systemName).apply(
                Optional.ofNullable(population)
                        .map(p -> new SingleDepositionPerGenotypeInitializer(p, depositionRate)),
                problem
        );

        aco.setNumberOfAnts(numberOfAnts);
        aco.setNumberOfIterations(numberOfIterations);
        aco.setAlpha(alpha);
        aco.setBeta(beta);
        aco.setRho(rho);

        List<AbstractDaemonActions> actions = new LinkedList<>();
        actions.add(new SaveResultDaemon(bsfResultSaver, aco));
        aco.setDaemonActions(actions) ;

        return aco;
    }

    @Bean
    public Function<List<PermutationSolution<Integer>>, ACO> acoSystemFactory(BSFResultSaver bsfResultSaver) {
        return population -> getAntSystem(bsfResultSaver, population);
    }
}
