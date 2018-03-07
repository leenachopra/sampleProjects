package selenium.testng.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.testng.BrowserSetup;

public class BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    private By signInButton = By.linkText("Sign in");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public SignInPage clickSignInBtn() {
        LOGGER.info("clicking on sign in button");
        WebElement signInBtnElement=driver.findElement(signInButton);
        if(signInBtnElement.isDisplayed()||signInBtnElement.isEnabled()) {
            signInBtnElement.click();
        } else  {
            LOGGER.info("Element not found");
        }
        return new SignInPage(driver);
    }

    public void clickImagesLink() {
        //It should have a logic to click on images link
        //And it should navigate to google images page
    }

    public String getPageTitle(){
        String title = driver.getTitle();
        return title;
    }

    public boolean verifyBasePageTitle() {
        String expectedPageTitle="Google";
        return getPageTitle().contains(expectedPageTitle);
    }

}