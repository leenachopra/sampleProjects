package jiracukeplugin.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jiracukeplugin.cucumber.Calculator;
import org.junit.Assert;
import utilities.jiracukeplugin.JiraRestClientHlpr;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cucumber.api.Scenario;

public class CalculatorRunSteps {
    private double total;
    private Calculator calc;
    private boolean currentRunStatus;
    private JiraRestClientHlpr jiraClientHelper = new JiraRestClientHlpr();
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorRunSteps.class);

    @Before
    public void init() {
        total = -999;
    }

    @Before
    public void setUp(Scenario scenario) {
        Collection<String> tagNames = scenario.getSourceTagNames();
        LOGGER.info("collect tag names::" + tagNames);
    }

    @Given("^a calculator I just turned on$")
    public void initCalcI_just_turned_on() {
        calc = new Calculator();
    }

    @When("^I add (-?\\d+) and (-?\\d+)$")
    public void addTwoNumbers(int arg1, int arg2) {

        calc.add(arg1, arg2);
    }

    @Then("^the result is (\\d+)$")
    public void validateResult(double result) {
        currentRunStatus = (total == result);
        Assert.assertEquals(total, result);
    }

    @When("^I add (\\d+) and (\\d+)$")
    public void adding(int arg1, int arg2) {
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @Given("^I press (.+)$")
    public void pressButton(String what) {

        calc.push(what);
    }

    @Before({"not @foo"})
    public void before() {
        System.out.println("Runs before scenarios *not* tagged with @foo");
    }

    @After
    public void after(Scenario scenario) {
        jiraClientHelper.updateJiraStatus(scenario.getSourceTagNames().iterator().next().replace("@", ""), currentRunStatus);
    }

    @Given("^the previous entries:$")
    public void thePreviousEntries(List<Entry> entries) {
        for (Entry entry : entries) {
            calc.push(entry.first);
            calc.push(entry.second);
            calc.push(entry.operation);
        }
    }

    public class Entry {
        Integer first;
        Integer second;
        String operation;
    }

}

