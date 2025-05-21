package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private Actions action;
    private WebDriverWait webDriverWait;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        webDriverWait=new WebDriverWait(driver, Duration.ofSeconds(30));

        action = new Actions(driver);
    }

    private By emailField = By.id("email");
    private By passwordField = By.id("pass");
    private By loginButton = By.id("send2");

    public void login(String email, String password) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver.findElement(emailField).sendKeys(email);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver.findElement(passwordField).sendKeys(password);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(loginButton));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center', inline: 'nearest' });", driver.findElement(loginButton));

        driver.findElement(loginButton).click();
    }


    public String getWelcomeMessage() {
        return driver.findElement(By.cssSelector("p.welcome-msg")).getText();
    }

}
