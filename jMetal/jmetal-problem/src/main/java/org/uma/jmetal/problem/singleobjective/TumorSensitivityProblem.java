package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.Double.NaN;
import static java.lang.Math.abs;

public class TumorSensitivityProblem extends AbstractDoubleProblem {

    public TumorSensitivityProblem() {
        this(2);
    }

    public TumorSensitivityProblem(Integer numberOfVariables) {
        setNumberOfVariables(numberOfVariables);
        setNumberOfObjectives(1);
        setName("TumorSensitivity");

//        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
//        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
//
//        lowerLimit.add(5.0);   //lower limit for p7 av 10
//        lowerLimit.add(1.8);   //lower limit for p8 av 2
//        lowerLimit.add(9.0);   //lower limit for p9 av 10
//        lowerLimit.add(90.0);  // lower limit for p10 av  100
//
//        upperLimit.add(15.0);
//        upperLimit.add(2.2);
//        upperLimit.add(11.0);
//        upperLimit.add(110.0);
//
//        setLowerLimit(lowerLimit);
//        setUpperLimit(upperLimit);
        setInitialLowerLimit();
        setInitialUpperLimit();
    }

    public void setInitialLowerLimit() {
        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        lowerLimit.add(11.0);   //lower limit for p7 av 10
        lowerLimit.add(2.1);   //lower limit for p8 av 2
//        lowerLimit.add(11.0);   //lower limit for p9 av 10
//        lowerLimit.add(110.0);  // lower limit for p10 av  100
        setLowerLimit(lowerLimit);
    }

    public void setInitialUpperLimit() {
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
        upperLimit.add(15.0);   //lower limit for p7 av 10
        upperLimit.add(3.0);   //lower limit for p8 av 2
//        upperLimit.add(15.0);   //lower limit for p9 av 10
//        upperLimit.add(150.0);  // lower limit for p10 av  100
        setUpperLimit(upperLimit);
    }

    public void setRuntimeLowerLimit() {
        List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
        lowerLimit.add(5.0);   //lower limit for p7 av 10
        lowerLimit.add(1.5);   //lower limit for p8 av 2
//        lowerLimit.add(5.0);   //lower limit for p9 av 10
//        lowerLimit.add(50.0);  // lower limit for p10 av  100
        setLowerLimit(lowerLimit);
    }

    public void setRuntimeUpperLimit() {
        List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());
        upperLimit.add(15.0);   //lower limit for p7 av 10
        upperLimit.add(3.0);   //lower limit for p8 av 2
//        upperLimit.add(15.0);   //lower limit for p9 av 10
//        upperLimit.add(150.0);  // lower limit for p10 av  100
        setUpperLimit(upperLimit);
    }


    @Override
    public void evaluate(DoubleSolution solution) {

        double p7 = solution.getVariableValue(0);
        double p8 = solution.getVariableValue(1);
//        double p9 = solution.getVariableValue(1);
//        double p10 = solution.getVariableValue(2);


        Process p;
        double tumorVolume = NaN;
        String command = "";
        try {
//            p = Runtime.getRuntime().exec(
//                    "../TumorCopy/simulator2/tumor 2 80 10000 0.1 1000 0.5 10 2 10 100 0.001 0.3 0.625 0.3205"
//                            + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
//                            +" 10 0.003 2 5 25 24 0.003 0.4");

//            command = "../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 "
//                    + p7 + " " + p8 + " " + p9 + " " + p10
//                    + " 0.001 0.3 0.625 0.3205"
//                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
//                    + " 10 0.003 2 5 25 24 0.003 0.4";

//            command = "mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 "
//                    + p7 + " " + p8 + " " + p9 + " " + p10
//                    + " 0.001 0.3 0.625 0.3205"
//                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
//                    + " 10 0.003 2 5 25 24 0.003 0.4";

            command = "mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 "
                    + p7 + " " + p8 + " " + 10 + " " + 100
                    + " 0.001 0.3 0.625 0.3205"
                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
                    + " 10 0.003 2 5 25 24 0.003 0.4";


//            command = "mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 "
//                    + p7 + " " + 2 + " " + p9 + " " + p10
//                    + " 0.001 0.3 0.625 0.3205"
//                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
//                    + " 10 0.003 2 5 25 24 0.003 0.4";

//            command = "../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 "
//                    + p7 + " " + 2 + " " + 10 + " " + 100
//                    + " 0.001 0.3 0.625 0.3205"
//                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
//                    + " 10 0.003 2 5 25 24 0.003 0.4";


            p = Runtime.getRuntime().exec(
                    command);
            System.out.println("Solver started: " + command);
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(p.getInputStream()));

//            Scanner scanner = new Scanner(br);//.useLocale(Locale.US);
//
            Scanner scanner = new Scanner(p.getInputStream()).useLocale(Locale.US);

            Scanner errScaner = new Scanner(p.getErrorStream());
            //            Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

            while (errScaner.hasNext()) {
                String next = errScaner.next();
                System.out.println("Read from err: " + next);
            }
            tumorVolume = Double.parseDouble(scanner.next());//scanner.nextDouble();
            scanner.close();
            System.out.println("Values on exit: "
                    + "Tumor: " + tumorVolume
                    + " p7: " + p7
                    + " p8: " + p8
//                    + " p9: " + p9
//                    + " p10: " + p10
            );
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            System.out.println("Unable to run solver: " + e.toString() + " " + command);
        }

        solution.setObjective(0, abs(397321
                - tumorVolume));
    }
}
