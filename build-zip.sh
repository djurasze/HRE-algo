#! /bin/bash
cd `dirname "${BASH_SOURCE[0]}"`

mvn clean
mvn package

rm -r ./HRE-AGH
mkdir HRE-AGH
cp -a ./target/hre-algorithm-1.0-jar-with-dependencies.jar ./HRE-AGH/HRE-AGH.1.0.jar
cp -a -R tests ./HRE-AGH/tests/
cp description-wsDD.xml ./HRE-AGH/description-wsDD.xml

rm HRE-AGH.zip
zip -r HRE-AGH.zip HRE-AGH

rm -r ./HRE-AGH
mvn clean
