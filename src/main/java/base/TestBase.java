package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;


import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excelReader = new ExcelReader
            ("C:\\Users\\anicolle\\eclipse-workspace\\DataDrivenFramework\\src\\main\\resources\\excel\\testData.xlsx");
    public static WebDriverWait wait;
    public ExtentReports reports = ExtentManager.getInstance();
    public static ExtentTest test;

    @BeforeSuite
    public void setUp(){
        if(driver == null){
            FileInputStream fileInputStream =
                    null;
            try {
                fileInputStream = new FileInputStream
                        (System.getProperty("user.dir") +
                                "\\src\\main\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fileInputStream);
                log.debug("Config file loaded!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileInputStream = new FileInputStream(System.getProperty("user.dir") +
                        "\\src\\main\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fileInputStream);
                log.debug("OR file loaded!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (config.getProperty("browser")){
                case "firefox":
                {
                    System.setProperty("webdriver.gecko.driver",
                            "src\\main\\resources\\executables\\geckodriver.exe");
                    ProfilesIni profile = new ProfilesIni();
                    FirefoxProfile testprofile = profile.getProfile("SeleniumProfile");
                    FirefoxOptions opt = new FirefoxOptions();
                    opt.setProfile(testprofile);
                    driver = new FirefoxDriver(opt);
                    log.debug("Firefox launched");
                    break;
                }
                case "opera":
                {
                    System.setProperty("webdriver.opera.driver",
                            "C:\\utils\\operadriver_win64\\operadriver_win64\\operadriver.exe");

                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    OperaOptions options = new OperaOptions();
                    options.setBinary("src\\main\\resources\\executables\\operadriver.exe");
                    capabilities.setCapability(OperaOptions.CAPABILITY, options);

                    driver = new org.openqa.selenium.opera.OperaDriver();
                    log.debug("Opera launched");
                    break;
                }
                case "chrome":
                {
                    ChromeOptions ops = new ChromeOptions();
                    ops.addArguments("--disable-notifications");

                    System.setProperty("webdriver.chrome.driver",
                            "src\\main\\resources\\executables\\chromedriver.exe");
                    driver = new ChromeDriver(ops);
                    log.debug("Chrome launched");
                    break;
                }
                case "ie":
                {
                    DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);

                    System.setProperty("webdriver.ie.driver",
                            "src\\main\\resources\\executables\\IEDriverServer.exe");

                    driver = new InternetExplorerDriver(capabilities);
                    log.debug("IE launched");
                    break;
                }
                case "edge":
                {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    log.debug("Edge launched");
                    break;
                }
                default:{
                    System.out.println("Webdriver não informado. " +
                            "Escolha entre: chrome, firefox, ie, edge ou opera.");
                }
            }
            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to :" + config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts()
                    .implicitlyWait(Long.parseLong(config.getProperty("implicit.wait")),
                            TimeUnit.SECONDS);
            wait = new WebDriverWait(driver,5);
        }
    }

    public void click(String locator){
        if(locator.endsWith("_CSS"))
        {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        }
        else if(locator.endsWith("_XPATH"))
        {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        }
        else if(locator.endsWith("_ID"))
        {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }

            test.log(LogStatus.INFO, "Clicking on : " + locator);
    }

    public static void verifyEquals(String expected, String actual) throws IOException {
        try{
            Assert.assertEquals(actual, expected);

        }catch (Throwable throwable){
            TestUtil.captureScreenshot();
            //ReportNG
            Reporter.log("<br>"+"Verification failure : " + throwable.getMessage() + "<br>");
            Reporter.log("<br>");
            Reporter.log("<a target =\"_blank\" href="+ TestUtil.screenshotName +">Screenshot</a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            //Extent Reports
            test.log(LogStatus.FAIL, "Verification failed with exception : " + throwable.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

        }
    }

    public void type(String locator, String value) {
        if(locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        }
        else if(locator.endsWith("_XPATH"))
        {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        }
        else if(locator.endsWith("_ID"))
        {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }
        test.log(LogStatus.INFO, "Typing in : " + locator + " and entered values " + value);

    }

    static WebElement dropdown;

    public void select(String locator, String value){
        if(locator.endsWith("_CSS")){
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }
        else if(locator.endsWith("_XPATH"))
        {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        }
        else if(locator.endsWith("_ID")) {
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);

        String text = dropdown.getText();
            if(!text.equals("---Customer Name---") && (!text.equals("---Currency---"))){
                if(!value.isEmpty() || !value.isBlank()){
                    select.selectByVisibleText(value);
                }
            }

        test.log(LogStatus.INFO, "Select from drop down: " + locator + " and value is " + value);

    }

    public boolean isElementPresent( By by){
        try{
            driver.findElement(by);
            return true;
        }catch (NoSuchElementException exception){
            return false;
        }
    }

    @AfterSuite
    public void tearDown(){

        if(driver!=null){
            driver.quit();
        }

        log.debug("Test execution completed!");
    }
}
