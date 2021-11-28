package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void loginAsBankManager() throws InterruptedException {

            log.debug("Inside Login Test");
            driver.findElement(By.xpath(OR.getProperty("bmlBtn"))).click();
            Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn"))),
                    "Login not succesfully");
            log.debug("Login successfully executed!");

//            Assert.fail("Login not successful");



    }

}
