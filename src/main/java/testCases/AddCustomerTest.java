package testCases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {

    //@Test(dataProvider = "getData")
    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomer(String firstName, String lastName, String postCode, String alertText) throws InterruptedException {
        click("addCustBtn_XPATH");
        type("firstName_XPATH", firstName);
        type("lastName_XPATH", lastName);
        type("postCode_XPATH", postCode);
        click("addBtn_XPATH");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alertText));
        Thread.sleep(2000);
        alert.accept();
        Thread.sleep(2000);

        Assert.fail("Customer not added successfully");
    }
}



