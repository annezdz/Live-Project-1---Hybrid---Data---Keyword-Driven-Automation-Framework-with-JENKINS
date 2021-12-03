package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {

    @Test
    public void bankManagerLoginTest() throws InterruptedException, IOException {

            //verifyEquals("abc","def");
            Thread.sleep(2000);

            log.debug("Inside Login Test");
            click("bmlBtn_XPATH");
            Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn_XPATH"))),
                    "Login not succesfully");
            log.debug("Login successfully executed!");

           //Assert.fail("Login not successful");



    }

}
