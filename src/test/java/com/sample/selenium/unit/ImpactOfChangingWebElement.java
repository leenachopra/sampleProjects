package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import static junit.framework.TestCase.assertTrue;

public class ImpactOfChangingWebElement {

    private String testUrl;
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeDriverTest.class);
    private static String OS = System.getProperty("os.name").toLowerCase();


    @Before
    public void prepare() {
        //setup chromedriver

        testUrl = "http://www.google.com";
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");

        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();
        assertTrue(driver != null);
        //maximize window
        driver.manage().window().maximize();
        driver.get(testUrl);

    }

    //Intentially want this test to fail to show that when developement
    // changes a webelements, how flacky the test can become and
    // eventually a maintenance nightmare.
    @Test
    public void changeWebElement() throws IOException {
        LOGGER.info("OS::" + OS);

        if (OS.indexOf("win") >= 0) {

            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("Browserstack");
            element.sendKeys((Keys.ENTER));

            JavascriptExecutor jse = (JavascriptExecutor) driver;

            //locator changed to name of q1
            jse.executeScript("document.getElementById('lst-ib').setAttribute('name', 'q1')");

            // here we discover it again to show failure when a webelement changes
            element = driver.findElement(By.name("q1")); // if we change this to q1, the test will pass.
            element.clear();
            element.sendKeys("saucelabs"); //perfecto
            element.sendKeys((Keys.ENTER));

        }
    }

    @After
    public void teardown() throws IOException {
        driver.quit();
    }
}
