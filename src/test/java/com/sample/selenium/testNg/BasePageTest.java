package com.sample.selenium.testNg;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import selenium.testng.BrowserSetup;
import selenium.testng.pageobjects.BasePage;

public class BasePageTest extends BrowserSetup{

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @Test
    public void verifyHomePage() {
        System.out.println("Home page test...");
        BasePage basePage = new BasePage(driver);
        Assert.assertTrue("Home page title doesn't match", basePage.verifyBasePageTitle());
    }

}
