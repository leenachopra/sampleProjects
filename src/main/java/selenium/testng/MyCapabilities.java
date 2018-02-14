package selenium.testng;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCapabilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCapabilities.class);
    public MyCapabilities() { }

    //capability.setCapability("browserName", "Chrome");
    //capability.setCapability("browserVersion", "49");
    //capability.setCapability("platformName", "Windows");
    //capability.setCapability("platformVersion",10);


    public DesiredCapabilities setCapabilities(String browserName, String browserVersion, String platformName,
                                               String platformVersion, String remoteWebDriver) {
        LOGGER.info("browserName: " + browserName
                + "browserVersion: " + browserVersion
                + "platformName: " + platformName
                + "platformVersion: " + platformVersion
                + "remoteWebDriver: " + remoteWebDriver);
        DesiredCapabilities capability = null;

        switch (browserName) {
            case "Chrome":
                capability = DesiredCapabilities.chrome();
                capability.setCapability("browserName", "chrome");
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                capability.setCapability("browserVersion", browserVersion);
                capability.setCapability("platformName", platformName);
                capability.setCapability("platformVersion",platformVersion);
                break;
            case "Firefox":
                capability = DesiredCapabilities.firefox();
                capability.setCapability("marionette", true);
                capability.setCapability("browserName", "firefox");
                capability.setCapability("browserVersion", browserVersion);
                capability.setCapability("platformName", platformName);
                capability.setCapability("platformVersion",platformVersion);
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                break;
            case "MicrosoftEdge":
                capability = DesiredCapabilities.internetExplorer();
                capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                capability.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
                capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                capability.setCapability("browserName", "MicrosoftEdge");
                capability.setCapability("browserVersion", browserVersion);
                capability.setCapability("platformName", platformName);
                capability.setCapability("platformVersion",platformVersion);
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                break;
            case "safari":
                capability = DesiredCapabilities.iphone();
                capability.setCapability("browserName", "safari");
                //stable safari version
                capability.setCapability("browserVersion", "11");
                capability.setCapability("platformName", "MAC");
                capability.setCapability("platformVersion",platformVersion);
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                break;
            case "iPhone":
                capability = DesiredCapabilities.iphone();
                capability.setCapability("browserName", "iPhone");
                capability.setCapability("browserVersion", browserVersion);
                capability.setCapability("platformName", "MAC");
                capability.setCapability("platformVersion",platformVersion);
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                break;
            case "android":
                capability = DesiredCapabilities.android();
                capability.setCapability("browserName", "iPhone");
                capability.setCapability("browserVersion", browserVersion);
                capability.setCapability("platformName", "MAC");
                capability.setCapability("platformVersion",platformVersion);
                if (remoteWebDriver.equals("Browserstack")) {
                    capability.setCapability("browserstack.user", "lchopra");
                    capability.setCapability("browserstack.key", "someKey");
                }
                break;
            default:
                LOGGER.info("browserName: " + browserName
                        + "browserVersion: " + browserVersion
                        + "platformName: " + platformName
                        + "platformVersion: " + platformVersion
                        + "remoteWebDriver: " + remoteWebDriver
                        + " is invalid.");

        }
        return capability;
    }
}
