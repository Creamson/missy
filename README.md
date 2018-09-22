# Ant-Evolutionary Hybrid

## To create a jar file:

Install dependencies into the local repository
(we skip tests because with our modifications they don't pass) 
```
cd jacof
mvn clean install -DskipTests
cd ../jMetal
mvn clean install -DskipTests
```
Build the jar file
```
cd ../hEvoAnt
mvn clean package
```
Now we have out jar file under `hEvoAnt/target/hEvoAnt-1.0.0-jar-with-dependencies.jar`
(do not confuse with `hEvoAnt-1.0.0.jar`, which is also in that directory).
This is the jar file we will eventually run on Zeus.

## Configuration 101
### By environment variables:

When running the application you will need to specify all the necessary options with `-D`, ex. (obviously not all available options were specified)

`java -jar -Daco.problemPath=aco/kroB100.tsp -Daco.systemName=antSystem -Dgenetic.problemPath=genetic/kroB100.tsp -Dgenetic.algorithmType=generational  hEvoAnt-1.0.0-jar-with-dependencies.jar`

Make sure the jar path comes after the options. 

### By passing a properties file

Technically should be possible but couldn't get it to work.