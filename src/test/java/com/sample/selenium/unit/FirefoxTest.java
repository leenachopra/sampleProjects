package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@Ignore
public class FirefoxTest {

    private String testUrl;
    private WebDriver driver;

    @Before
    public void prepare() {

        testUrl = "http://www.google.com";

        System.setProperty("webdriver.gecko.driver","webdriver/geckodriver.exe");
        //System.setProperty("webdriver.firefox.marionette","webdriver/geckodriver.exe");


        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        //FirefoxProfile profile = new FirefoxProfile();
        //profile.setPreference("browser.startup.homepage", testUrl);
        driver = new FirefoxDriver(capabilities);
        //maximize window
        driver.manage().window().maximize();
        driver.get(testUrl);
    }

    @Test
    public void testTitle() throws IOException {
        assertTrue("The page title should be changed as expected",
            (new WebDriverWait(driver, 3))
                .until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return d.getTitle().equals("Google");
                    }
                })
        );
    }

    @After
    public void teardown() throws IOException {
        driver.quit();
    }
}
