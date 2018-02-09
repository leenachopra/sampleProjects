package com.sample.selenium.testNg;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import selenium.testng.BrowserSetup;
import selenium.testng.pageobjects.BasePage;
import selenium.testng.pageobjects.SignInPage;

public class SignInPageTest extends BrowserSetup{
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInPageTest.class);
    private WebDriver driver;
    private SignInPage signInPage;
    private BasePage basePage;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void verifySignInFunction() {
        LOGGER.info("Sign In functionality details...");
        basePage = new BasePage(driver);
        signInPage = basePage.clickSignInBtn();
        Assert.assertTrue("Sign In page title doesn't match", signInPage.verifySignInPageTitle());
        Assert.assertTrue("Page text not matching", signInPage.verifySignInPageText());
        Assert.assertTrue("Unable to sign in", signInPage.verifySignIn());

    }

}
