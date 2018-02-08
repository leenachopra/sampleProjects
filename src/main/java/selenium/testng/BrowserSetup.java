package selenium.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

/** Sample code from http://www.seleniumeasy.com/selenium-tutorials/simple-page-object-model-framework-example **/
public class BrowserSetup
{
    private WebDriver driver;
    static String driverPath = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserSetup.class);

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL);
                break;
            case "ie":
                driver = initIEDriver(appURL);
                break;
            default:
                LOGGER.info("browser : " + browserType
                        + " is invalid, Launching Firefox as browser of choice..");
                driver = initFirefoxDriver(appURL);
        }
    }

    private static WebDriver initChromeDriver(String appURL) {
        LOGGER.info("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        driver.get(appURL);
        return driver;
    }

    private static WebDriver initFirefoxDriver(String appURL) {
        LOGGER.info("Launching Firefox browser..");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private static WebDriver initIEDriver(String appURL) {
        LOGGER.info("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", "webdriver/IEDriverServer.exe");
        WebDriver driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        //driver.get(appUrl);
        return driver;
    }

    @Parameters({ "browserType", "appURL" })
    @BeforeClass
    public void initBrowser(String browserType, String appURL) {
        try {
            setDriver(browserType, appURL);

        } catch (Exception e) {
            LOGGER.info("Error....." + e.getStackTrace());
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

