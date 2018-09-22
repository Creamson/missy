package pl.edu.agh.missy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.io.IOException;

@Configuration
//@PropertySource("classpath:application.properties")
public class ResultSavingConfiguration {
    @Value("${results.fileName:problem_algo_code.csv}")
    public String resultsFileName;

    @Bean
    public BSFResultSaver bsfResultSaver() throws IOException {
        return new BSFResultSaver(resultsFileName);
    }
}
