package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

@Ignore
public class IEDriverTest {

    private String testUrl;
    private WebDriver driver;

    @Before
    public void prepare() {
        testUrl = "http://www.google.com";
        //setup chromedriver
        System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");

        driver = new InternetExplorerDriver();
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
