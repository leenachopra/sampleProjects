package com.sample.selenium;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;


import static org.junit.Assert.assertTrue;

public class ChromeDriverTest {

    private String testUrl;
    private WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChromeDriverTest.class);

    @Before
    public void prepare() {
        //setup chromedriver

        testUrl = "https://leftstick.github.io/";
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");


        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        driver = new ChromeDriver();

        //maximize window
        driver.manage().window().maximize();

        // And now use this to visit myBlog
        // Alternatively the same thing can be done like this
        // driver.navigate().to(testUrl);
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
                        return d.getTitle().equals("我眼中软件工程人员该有的常识");
                    }
                })
        );
    }

    @After
    public void teardown() throws IOException {
        driver.quit();
    }
}
