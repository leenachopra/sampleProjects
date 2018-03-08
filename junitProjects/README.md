# sample Junit Projects
The repository contains projects of selenium using ChromeWebDriver, IEDriver, FirefoxFriver



## Prerequisites ##
* Have [java] 1.8X version (http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed
* Have [maven] 3.X version (http://maven.apache.org/)
* IDE used to develop is IntelliJ Community addtion.


## Execute automation tests (JUnit test types) ##
mvn clean install test 

## To see a report of the tests ##
## The html report will be located in The default report should be located in ${basedir}/target/site/surefire-report.html ##
mvn surefire-report:report 



## Execute jacoco
mvn clean package