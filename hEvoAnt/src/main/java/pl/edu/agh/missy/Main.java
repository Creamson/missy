package pl.edu.agh.missy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.edu.agh.missy.config.HybridConfiguration;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws IOException {

        ApplicationContext context = new AnnotationConfigApplicationContext(
                HybridConfiguration.class
        );


        HybridRunner runner = context.getBean(HybridRunner.class);
        runner.run();
    }
}
