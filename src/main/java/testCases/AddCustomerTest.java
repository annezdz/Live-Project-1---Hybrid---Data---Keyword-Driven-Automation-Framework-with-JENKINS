package testCases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest extends TestBase {

    @Test(dataProvider = "getData")
    public void addCustomer(String firstName, String lastName, String postCode, String alertText) throws InterruptedException {
        driver.findElement(By.xpath(OR.getProperty("addCustBtn"))).click();
        driver.findElement(By.xpath(OR.getProperty("firstName"))).sendKeys(firstName);
        driver.findElement(By.xpath(OR.getProperty("lastName"))).sendKeys(lastName);
        driver.findElement(By.xpath(OR.getProperty("postCode"))).sendKeys(postCode);
        driver.findElement(By.xpath(OR.getProperty("addBtn"))).click();

        Alert alert =  wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alertText));
        Thread.sleep(2000);
        alert.accept();
        Thread.sleep(2000);

        Assert.fail("Customer not added successfully");


    }

    @DataProvider
    public Object[][] getData() {
        String sheetName = "AddCustomerTest";
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



