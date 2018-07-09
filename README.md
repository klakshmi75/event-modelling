# Overview
This is a standalone java application which demonstarates how to find a recurring event with highest priority out of multiple such events on a given date. This mainly solves the problem of modelling reurring events planned on week day basis, i.e; every Monady, every other Monday, first Monday of every month, etc. It does not provide solution for date based events like 1st of every month, 2nd of every month, etc. We can extend the code easily to solve this problem too.

# Dependencies
jdk 8 or higher.

# Steps to run the application
1. Clone repository 
    [https://github.com/klakshmi75/event-modelling](https://github.com/klakshmi75/event-modelling)
2. Goto event-modelling directory on command line.
3. Execute the below commands.<br /> 
   $javac -d bin -sourcepath src -cp "lib/joda-time-2.10.jar:lib/play-java_2.10.jar" src/com/company/EventModellingDemo.java<br /> 
   $java -cp "bin:lib/joda-time-2.10.jar:lib/play-java_2.10.jar" com.company.EventModellingDemo
 
  

