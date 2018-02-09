package jiracukeplugin.cucumber.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

// the test output will be in sampleProjects/target/cucumber-html-reports/index.html
//To run the feature files, right mouse click on CucumberRunner class  in intelliJ
//TODO: need to get test working on Command prompt
@RunWith(Cucumber.class)
@CucumberOptions(  monochrome = true,
        features = "src/test/resources/features/",
        format = { "pretty","html:target/cucumber-html-reports",
                "json: cucumber-html-reports/cucumber.json" },
        dryRun = false,
        glue = "jiracukeplugin.cucumber.steps" )

public class CucumberRunner {
}
