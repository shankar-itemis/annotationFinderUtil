# annotationFinderUtil

A simple Java application which lists annotated Classes available in the given source dir / jar. This application recursively checks.
The list of classes which has annotations are listed in console.

## System Requirements:

 1. BurningWave Core
 2. JDK 1.8

## How to Build

 This application makes use of Maven configurations. Just run mvn install. The artifacts are generated in the target dir.

## To execute

 After building run the jar and supply the source dir or file as command line arg as below,
 
 java -cp com.itemis.annotationfinder-0.0.1-SNAPSHOT.jar com.itemis.annotationfinder.ApplicationMain /workspace/installation/xtext/
