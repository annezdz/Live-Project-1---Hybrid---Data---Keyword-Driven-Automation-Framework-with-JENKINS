package base;

import io.github.bonigarcia.wdm.WebDriverManager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.ExcelReader;

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
    public static final Logger log = LoggerFactory.getLogger("log4j.category.br.com.pacote1");
    public static ExcelReader excelReader = new ExcelReader
            ("C:\\Users\\anicolle\\eclipse-workspace\\DataDrivenFramework\\src\\main\\resources\\excel\\testData.xlsx");
    public static WebDriverWait wait;

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
