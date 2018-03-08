# sampleProjects
The repository contains projects of selenium using ChromeWebDriver, IEDriver, FirefoxFriver using page object design.
written in TestNg for google website.



## Prerequisites ##
* Have [java] 1.8X version (http://www.oracle.com/technetwork/java/javase/downloads/index.html) installed
* Have [maven] 3.X version (http://maven.apache.org/)
* IDE used to develop is IntelliJ Community addtion.


## Execute automation tests (cucumber, testNg and JUnit test types) ##
```bash
mvn clean install test -DbrowserName=Chrome -DbrowserVersion=64.0 -DplatformName=Windows -DplatformVersion=10 -DappURL=https://www.google.co.in/

## Execute jacoco
mvn clean package

#The result looks as following:
##![](./docs/img/autotests.gif)