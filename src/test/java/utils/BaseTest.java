package utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;


public class BaseTest {
//    protected WebDriver driver;
//    @BeforeClass
//    public void setUp() {
//        driver = DriverFactory.getDriver();
//        driver.get(ConfigReader.get("url"));
//    }
//
//    @AfterClass
//    public void tearDown() {
//        DriverFactory.quitDriver();
//    }
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReporter.getExtentReport();
    }

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.get("url"));
    }

    @BeforeMethod
    public void setUpMethod(Method method) {
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "✅ Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "⚠️ Test Skipped: " + result.getThrowable());
        }
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }


}
