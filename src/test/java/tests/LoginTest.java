package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ZapUtil;

public class LoginTest extends BaseTest {

    @Test
    public void validUserShouldLoginSuccessfully() throws Exception {
        // Arrange
        LoginPage loginPage = new LoginPage(driver);

        String validUsername = "student";
        String validPassword = "Password123";

        // Act: perform login
        loginPage.loginAs(validUsername, validPassword);

        // Assert: functional checks
        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Expected login to be successful and Logged In page to be displayed"
        );

        Assert.assertTrue(
                loginPage.isLogoutButtonVisible(),
                "Expected logout button to be visible after successful login"
        );

        // Security: run ZAP scan for the application and save report
        // Make sure ZAP is running and proxy is configured in BaseTest
        ZapUtil.runScanAndSaveReport("https://practicetestautomation.com");
    }
}