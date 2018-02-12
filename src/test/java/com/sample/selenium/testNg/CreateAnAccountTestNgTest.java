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
import selenium.testng.pageobjects.CreateAccountPage;

public class CreateAnAccountTestNgTest extends BrowserSetup{
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAnAccountTestNgTest.class);

    private WebDriver driver;
    private SignInPage signInPage;
    private BasePage basePage;
    private CreateAccountPage createAccountPage;

    @BeforeClass
    public void setUp() {

        driver = getDriver();
    }

    @Test
    public void verifyCreateAnAccountPage() {
        LOGGER.info("Create An Account page test...");
        basePage = new BasePage(driver);
        signInPage = basePage.clickSignInBtn();
        createAccountPage = signInPage.clickonCreateAnAccount();
        Assert.assertTrue("Page title not matching", createAccountPage.verifyPageTitle());
        Assert.assertTrue("Page text not matching", createAccountPage.verifyCreateAccountPageText());
    }

}
