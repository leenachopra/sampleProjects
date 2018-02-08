package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
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

        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        //DesiredCapabilities caps = DesiredCapabilities.firefox();
        //caps.setCapability("version", "latest");
        //caps.setCapability("platform", Platform.WINDOWS);
        //caps.setCapability("name", "Testing Selenium");

        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);
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
