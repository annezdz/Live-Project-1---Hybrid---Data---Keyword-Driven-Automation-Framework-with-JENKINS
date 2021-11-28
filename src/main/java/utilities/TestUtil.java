package utilities;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class TestUtil extends TestBase {

    public static String screenshotPath;
    public static String screenshotName;


    public static void captureScreenshot() throws IOException {

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        screenshotName = "error";
        FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +
                "\\test-output\\surefire-reports\\" + screenshotName + ".jpg"));
    }
}
