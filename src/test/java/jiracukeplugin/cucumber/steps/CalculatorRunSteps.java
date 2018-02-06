package jiracukeplugin.cucumber.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jiracukeplugin.cucumber.Calculator;
import utilities.jiracukeplugin.JiraRestClientHlpr;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cucumber.api.Scenario;

import static org.junit.Assert.assertEquals;

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
        Collection<String> tagNames = new scenario.getSourceTagNames();
        Logger.info("collect tag names::" + tagNames);
    }

    @Given("^a calculator I just turned on$")
    public void openCalc() {
        calc = new Calculator();
    }

    @When("^I add (-?\\d+) and (-?\\d+)$")
    public void addTwoNumbers(int arg1, int arg2) {
        calc.add(arg1, arg2);
    }

    @Given("^I press (.+)$")
    public void pressButtonOnCalc(String what) {
        calc.push(what);
    }

    @Then("^the result is (\\d+)$")
    public void validateResult(double result) {
        currentRunStatus = (total == result);
        assertEquals(total, result);
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

    @After
    public void after(Scenario scenario) {
        jiraClientHelper.updateJira(scenario.getSourceTagNames().iterator().next().replace("@", ""), currentRunStatus);
    }
}

