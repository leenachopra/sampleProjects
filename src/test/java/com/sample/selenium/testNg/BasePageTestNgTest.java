package com.sample.selenium.testNg;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import selenium.testng.BrowserSetup;
import selenium.testng.pageobjects.BasePage;

public class BasePageTestNgTest extends BrowserSetup{

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePageTestNgTest.class);
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test
    public void verifyHomePage() {
        LOGGER.info("Home page test...");
        BasePage basePage = new BasePage(driver);
        Assert.assertTrue("Home page title doesn't match", basePage.verifyBasePageTitle());
    }

}
