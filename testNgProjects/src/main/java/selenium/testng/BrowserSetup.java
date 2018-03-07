package selenium.testng;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Sample code from http://www.seleniumeasy.com/selenium-tutorials/simple-page-object-model-framework-example **/
public class BrowserSetup
{
    private WebDriver driver;
    static String driverPath = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserSetup.class);

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(String browserName, String appURL) {
        switch (browserName) {
            case "Chrome":
                driver = initChromeDriver(appURL);
                break;
            case "Firefox":
                driver = initFirefoxDriver(appURL);
                break;
            case "IE":
                driver = initIEDriver(appURL);
                break;
            default:
                LOGGER.info("browserName : " + browserName
                        + " is invalid, Launching Chrome as browser of choice.");
                driver = initChromeDriver(appURL);
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

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        //FirefoxProfile profile = new FirefoxProfile();
        //profile.setPreference("browser.startup.homepage", testUrl);
        WebDriver driver = new FirefoxDriver(capabilities);
        //maximize window
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private static WebDriver initIEDriver(String appURL) {
        LOGGER.info("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", "webdriver/IEDriverServer.exe");

        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capability.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

        //System.setProperty("webdriver.ie.driver", "webdriver/IEDriverServer.exe");
        //WebDriver driver = new InternetExplorerDriver();

        System.setProperty("webdriver.edge.driver", "webdriver/MicrosoftWebDriver.exe");
        WebDriver driver = new EdgeDriver(capability);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        //driver.get(appUrl);
        return driver;
    }

    private static WebDriver initBrowserStack(String appURL, String browserName, String browserVersion, String platformName,
                                              String platformVersion) throws MalformedURLException {
        DesiredCapabilities capability = new MyCapabilities().setCapabilities(browserName,  browserVersion,  platformName,
                 platformVersion, "Browserstack");
        WebDriver driver = new RemoteWebDriver(
                new URL("http://hub.browserstack.com/wd/hub/"), capability);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    private static WebDriver initSeleniumGrid(String appURL, String browserName, String browserVersion, String platformName,
                                              String platformVersion) throws MalformedURLException {
        DesiredCapabilities capability = new MyCapabilities().setCapabilities(browserName,  browserVersion,  platformName,
                platformVersion, "Grid");
        WebDriver driver = new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"), capability);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        return driver;
    }

    @Parameters({ "browserName", "browserVersion",  "platformName", "platformVersion", "appURL" })
    @BeforeClass
    public void initBrowser(String browserName, String browserVersion, String platformName, String platformVersion, String appURL) {
        try {
            setDriver(browserName, appURL);

        } catch (Exception e) {
            LOGGER.info("Error....." + e.getStackTrace());
        }
    }

    //take Page screenshot in selenium webdriver with java, pngFile
    public void getScreenShotOfPage(String pngFileName) throws Exception
    {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //The below method will save the screen shot in target/screenShots folder
        FileUtils.copyFile(scrFile, new File("target/screenShots/" + timestamp() + pngFileName));
    }

    //Locate the element for which you want screenshot, pngFile
    public void getScreenShotOfWebElement(WebElement element, String pngFileName) throws IOException {
        //Capture entire page screenshot as File.
        //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Used selenium Point class to get x y coordinates of Image element.
        //get location(x y coordinates) of the element.
        Point point = element.getLocation();
        int xcordinate = point.getX();
        int ycordinate = point.getY();

        //Used selenium getSize() method to get height and width of element.
        //Retrieve width of element.
        int imageWidth = element.getSize().getWidth();
        //Retrieve height of element.
        int imageHeight = element.getSize().getHeight();

        //Reading full image screenshot.
        BufferedImage img = ImageIO.read(screen);

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage destination = img.getSubimage(xcordinate, ycordinate, imageWidth, imageHeight);
        ImageIO.write(destination, "png", screen);

        //The below method will save the screen shot in target/screenShots folder
        FileUtils.copyFile(screen, new File("target/screenShots/" + timestamp() + pngFileName));
    }


    public String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}