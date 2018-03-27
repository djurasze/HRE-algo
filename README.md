# HRE-algo
HRE-algorithm module for Decision-Deck platform

## Prerequisites

- Java 8
- Maven (tested with 3.5.2 version)


## Build

It is important to run for the first time separately ```mvn clean``` and ```mvn package``` because when ```mvn clean``` is executed maven will add to local repository lib with XMCDA library. 
```
mvn clean 
mvn package
```

generated jar with all necessary dependencies:

```
./target/hre-algorithm-1.0-jar-with-dependencies.jar
```

## Running app

For xmcda files with version 3.0.0:
```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i <inputDir> -o <outputDir> --v3
```

For xmcda files with version 2.2.2:

```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i <inputDir> -o <outputDir> --v2
```

examples:

```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i ./test/inputV3 -o ./test/outputV3 --v3
```

```
java -jar ./target/hre-algorithm-1.0-jar-with-dependencies.jar -i ./test/inputV2 -o ./test/outputV2 --v2
```

More info about XMCDA format on [Decision Deck](https://www.decision-deck.org/project/)

## procedure to build the HRE-AGH.zip

run script build-zip.sh

This script will generate HRE-AGH.zip with:

tests directory
description-wsDD.xml 
HRE-AGH.1.0.jar




