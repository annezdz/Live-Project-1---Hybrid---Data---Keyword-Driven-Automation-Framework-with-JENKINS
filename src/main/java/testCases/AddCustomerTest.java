package testCases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    //@Test(dataProvider = "getData")
    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {



        click("addCustBtn_XPATH");
        type("firstName_XPATH", data.get("firstName"));
        type("lastName_XPATH", data.get("lastName"));
        type("postCode_XPATH",data.get("postCode"));
        click("addBtn_XPATH");
        Thread.sleep(2000);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alertText")));
        //Thread.sleep(2000);
        alert.accept();
        Thread.sleep(2000);

        //Assert.fail("Customer not added successfully");
    }
}



