package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


import static org.junit.Assert.assertTrue;

public class ChromeDriverTest {

    private String testUrl;
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeDriverTest.class);

    @Before
    public void prepare() {
        //setup chromedriver

        testUrl = "http://www.google.com";
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");

        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();
        //maximize window
        driver.manage().window().maximize();
        driver.get(testUrl);
    }

    @Test
    public void testTitle() throws IOException {

        assertTrue("The page title should be changed as expected",
            (new WebDriverWait(driver, 5))
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
