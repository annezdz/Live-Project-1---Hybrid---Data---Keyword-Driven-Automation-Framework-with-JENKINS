package base;

import io.github.bonigarcia.wdm.WebDriverManager;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;

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
                    break;
                }
                case "chrome":
                {
                    ChromeOptions ops = new ChromeOptions();
                    ops.addArguments("--disable-notifications");

                    System.setProperty("webdriver.chrome.driver",
                            "src\\main\\resources\\executables\\chromedriver.exe");
                    driver = new ChromeDriver(ops);
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
                    break;
                }
                case "edge":
                {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                }
                default:{
                    System.out.println("Webdriver não informado. " +
                            "Escolha entre: chrome, firefox, ie, edge ou opera.");
                }
            }
            driver.get(config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts()
                    .implicitlyWait(Long.parseLong(config.getProperty("implicit.wait")),
                            TimeUnit.SECONDS);
        }
    }

    @AfterSuite
    public void tearDown(){

        if(driver!=null){
            driver.quit();
        }
    }
}
