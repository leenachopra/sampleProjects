# sample Cukes Projects
The repository contains projects for jira-cucumber code sample.

## Prerequisites ##
* Have [java] 1.8X version (http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed
* Have [maven] 3.X version (http://maven.apache.org/)
* IDE used to develop is IntelliJ Community addtion.


## Execute automation tests (cucumber test types) ##
```bash
mvn clean install 

## Execute jacoco
mvn clean package

## To invoke the gherkin feature tests via intelliJ, just right click on the Runner class and run it
## The test execution report  will be in the area specfied by "pretty","html:target/cucumber-html-reports"
## or whatever the runner class mentions.
## The jira hook has been code but will not be called as we need the jiraURL to be defined and the Auth username and password


#The result looks as following:
##![](./docs/img/autotests.gif)