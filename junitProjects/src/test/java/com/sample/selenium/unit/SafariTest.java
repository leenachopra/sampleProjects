package com.sample.selenium.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Ignore
public class SafariTest {

    private String testUrl;
    private WebDriver driver;

    @Before
    public void prepare() {

        testUrl = "http://www.yahoo.com";

        driver = new SafariDriver();
        driver.manage().window().maximize();
        driver.get(testUrl);
    }

    @Test
    public void testTitle() throws IOException {

        // Find elements by attribute lang="READ_MORE_BTN"
        List<WebElement> elements = driver
                .findElements(By.cssSelector("[lang=\"READ_MORE_BTN\"]"));

        //Click the selected button
        elements.get(0).click();

        assertTrue("The page title should be changed as expected",
            (new WebDriverWait(driver, 5))
                .until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return d.getTitle().equals("Yahoo");
                    }
                })
        );
    }

    @After
    public void teardown() throws IOException {
        driver.quit();
    }
}
