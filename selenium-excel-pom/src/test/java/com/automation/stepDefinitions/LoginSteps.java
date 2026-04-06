package com.automation.stepDefinitions;

import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.utils.ExcelReader;
import com.automation.utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class LoginSteps {

    private static final String EXCEL_PATH  = "src/test/resources/testdata/testdata.xlsx";
    private static final String SHEET_LOGIN = "Login";

    private final LoginPage loginPage = new LoginPage();
    private final HomePage  homePage  = new HomePage();

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login page not loaded");
        ExtentReportManager.getTest().log(Status.INFO, "Login page loaded successfully");
    }

    @When("the user logs in using excel row {int}")
    public void theUserLogsInUsingExcelRow(int rowIndex) {
        List<Map<String, String>> data = ExcelReader.getData(EXCEL_PATH, SHEET_LOGIN);
        Map<String, String> row = data.get(rowIndex - 1);

        String username = row.get("username");
        String password = row.get("password");

        ExtentReportManager.getTest().log(Status.INFO,
                "Logging in with username: " + username + " (row " + rowIndex + ")");
        loginPage.login(username, password);
    }

    @When("the user logs in with {string} and {string}")
    public void theUserLogsInWith(String username, String password) {
        ExtentReportManager.getTest().log(Status.INFO,
                "Logging in with username: " + username);
        loginPage.login(username, password);
    }

    @Then("the user should be on the home page")
    public void theUserShouldBeOnTheHomePage() {
        Assert.assertTrue(homePage.isLoaded(), "Home page not loaded after login");
        ExtentReportManager.getTest().log(Status.PASS,
                "Login successful — home page title: " + homePage.getPageTitle());
    }

    @Then("an error message {string} should be displayed")
    public void anErrorMessageShouldBeDisplayed(String expectedError) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not visible");
        String actual = loginPage.getErrorMessage();
        Assert.assertEquals(actual, expectedError, "Error message mismatch");
        ExtentReportManager.getTest().log(Status.PASS, "Error message verified: " + actual);
    }

    @Then("the login should fail with the error from excel row {int}")
    public void theLoginShouldFailWithErrorFromExcel(int rowIndex) {
        List<Map<String, String>> data = ExcelReader.getData(EXCEL_PATH, SHEET_LOGIN);
        String expectedError = data.get(rowIndex - 1).get("expectedError");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not visible");
        String actual = loginPage.getErrorMessage();
        Assert.assertEquals(actual, expectedError, "Error message mismatch");
        ExtentReportManager.getTest().log(Status.PASS, "Error verified: " + actual);
    }
}
