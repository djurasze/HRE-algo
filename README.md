# HRE-algo
HRE-algorithm module for Decision-Deck platform

## Prerequisites

- Java 8
- Maven (tested with 3.5.2 version)


## Build

```
mvn clean 
mvn package
```

generated jar with all necessary dependencies:

```
./target/hre-algorithm-1.0-jar-with-dependencies.jar
```

## Running app

```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i <inputDir> -o <outputDir> 
```

example:

```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i ./test/input -o ./test/output 
```

More info about XMCDA format on [Decision Deck](https://www.decision-deck.org/project/)

