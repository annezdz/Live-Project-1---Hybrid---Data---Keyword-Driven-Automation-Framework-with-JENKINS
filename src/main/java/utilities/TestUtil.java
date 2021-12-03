package utilities;

import base.TestBase;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

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

        Object[][] data = new Object[rows - 1][1];

        Hashtable<String, String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            table = new Hashtable<String, String>();
            for (int colNum = 0; colNum < cols; colNum++) {
                table.put(excelReader.getCellData(sheetName, colNum, 1),
                        excelReader.getCellData(sheetName, colNum, rowNum));
                    data[rowNum - 2][0] = table;
            }
        }
        return data;
    }


    public static boolean isTestRunnable(String testName, ExcelReader excel) {

//        String sheetName="test_suite";
//        int count = 0;
//        int rows = excel.getRowCount(sheetName);
//        for(int rNum=2; rNum<=rows; rNum++){
//
//            String testCase = excel.getCellData(sheetName, "TCID", rNum);
//
//            if(testCase.equalsIgnoreCase(testName)) {
//                String runmode = excel.getCellData(sheetName, "Runmode", rNum);
//
//                if(runmode.equalsIgnoreCase("Y"))
//                    return true;
//                else
//                    return false;
//            }
//        }
//        return false;
        String sheetName = "test_suite";
        int count = 0;
        int rows = excel.getRowCount(sheetName);
        while (count < rows) {
            String testCase = excel.getCellData(sheetName, "TCID", count);

            if (testCase.equalsIgnoreCase(testName)) {
                String runmode = excel.getCellData(sheetName, "Runmode", count);

                if (runmode.equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;
            }
            count++;
        }
        return false;
    }

}
