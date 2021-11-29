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
            click("bmlBtn_XPATH");
            Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn_XPATH"))),
                    "Login not succesfully");
            log.debug("Login successfully executed!");

//            Assert.fail("Login not successful");



    }

}
