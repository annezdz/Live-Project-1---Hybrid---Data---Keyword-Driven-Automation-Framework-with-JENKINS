package testCases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(String customer, String currency) {

    }

//    @DataProvider
//    public Object[][] getData() {
//        String sheetName = "OpenAccountTest";
//        int rows = excelReader.getRowCount(sheetName);
//        int cols = excelReader.getColumnCount(sheetName);
//
//        Object[][] data = new Object[rows - 1][cols];
//
//        for (int rowNum = 2; rowNum <= rows; rowNum++) {
//            for (int colNum = 0; colNum < cols; colNum++) {
//                data[rowNum - 2][colNum] = excelReader.getCellData(sheetName, colNum, rowNum);
//            }
//        }
//        return data;
//    }
}
