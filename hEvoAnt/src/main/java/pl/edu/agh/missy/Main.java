package pl.edu.agh.missy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.edu.agh.missy.config.AcoConfiguration;
import pl.edu.agh.missy.config.GeneticConfiguration;
import pl.edu.agh.missy.config.HybridConfiguration;
import pl.edu.agh.missy.results.BSFResultSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String [] args) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(
                HybridConfiguration.class
        );


        HybridRunner runner = context.getBean(HybridRunner.class);
        runner.run();
    }
}
