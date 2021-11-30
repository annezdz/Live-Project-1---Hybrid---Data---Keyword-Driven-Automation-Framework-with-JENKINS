package utilities;

import base.TestBase;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class TestUtil extends TestBase {

    public static String screenshotPath;
    public static String screenshotName;


    public static void captureScreenshot() throws IOException {

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        screenshotName = "error.png";
        FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") +
                "\\target\\surefire-reports\\html\\" + screenshotName));
    }

    @DataProvider(name = "dp")
    public static Object[][] getData(Method method) {
        String sheetName = method.getName().toUpperCase();
        int rows = excelReader.getRowCount(sheetName);
        int cols = excelReader.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            for (int colNum = 0; colNum < cols; colNum++) {
                data[rowNum - 2][colNum] = excelReader.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }


}
