package listeners;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utilities.TestUtil;

import java.io.IOException;
import java.util.Locale;

public class CustomListeners extends TestBase implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = reports.startTest(iTestResult.getName().toUpperCase());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

        test.log(LogStatus.PASS, iTestResult.getName().toUpperCase()+ "PASS");
        reports.endTest(test);
        reports.flush();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        System.setProperty("org.uncommons.reportng.escape-output","false");
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(LogStatus.FAIL, iTestResult.getName().toUpperCase()+ "Failed with exception : " + iTestResult.getThrowable());
        //test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

                Reporter.log("Click to see screenshot");
        Reporter.log("Login successfully executed!");
        Reporter.log("<a target =\"_blank\" href="+ TestUtil.screenshotName +">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");

        Reporter.log("<a target =\"_blank\" href="+ TestUtil.screenshotName +">Screenshot</a>");

        reports.endTest(test);
        reports.flush();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
