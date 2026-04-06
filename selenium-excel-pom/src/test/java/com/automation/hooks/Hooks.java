package com.automation.hooks;

import com.automation.utils.ConfigReader;
import com.automation.utils.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String browser = ConfigReader.get("browser");
        DriverManager.initDriver(browser);
        DriverManager.getDriver().get(ConfigReader.get("baseUrl"));

        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest(scenario.getName())
        );
        ExtentReportManager.getTest().log(Status.INFO, "Browser launched: " + browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
            ExtentReportManager.getTest().fail("Scenario failed — screenshot attached");
            ExtentReportManager.getTest().log(Status.FAIL, scenario.getName() + " FAILED");
        } else {
            ExtentReportManager.getTest().log(Status.PASS, scenario.getName() + " PASSED");
        }
        DriverManager.quitDriver();
    }

    @AfterAll
    public static void flushReports() {
        ExtentReportManager.flush();
    }
}
