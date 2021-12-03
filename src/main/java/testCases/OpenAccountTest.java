package testCases;

import base.TestBase;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.Hashtable;


public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {

        String text = "---Customer Name---";

        click("openAccountbtn_XPATH");
        Thread.sleep(2000);

        select("addCustomer_XPATH",data.get("customer"));
        select("addCurrency_XPATH", data.get("currency"));
        click("addProcessBtn_XPATH");
        Thread.sleep(2000);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alertText")));
        alert.accept();
        Thread.sleep(2000);
    }

}
