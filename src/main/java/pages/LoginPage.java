package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    // Locators
    private final By usernameInput = By.id("username");      // for practice-test-login
    private final By passwordInput = By.id("password");
    private final By loginButton   = By.id("submit");
    private final By successMessage = By.xpath("//h1[contains(.,'Logged In')]");
    private final By logoutButton   = By.xpath("//a[text()='Log out']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage typeUsername(String username) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void loginAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        clickLogin();
    }

    public boolean isLoginSuccessful() {
        return driver.findElements(successMessage).size() > 0;
    }

    public boolean isLogoutButtonVisible() {
        return driver.findElements(logoutButton).size() > 0;
    }
}