package utilities;

import base.TestBase;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public class TestUtil extends TestBase {

    public static String screenshotPath;
    public static String screenshotName;


    public static void captureScreenshot() throws IOException {

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        screenshotName = "error.png";
        FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +
                "\\target\\surefire-reports\\html\\" + screenshotName));
    }


}
