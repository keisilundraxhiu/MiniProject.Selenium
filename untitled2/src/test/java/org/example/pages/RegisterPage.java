package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By firstName = By.id("firstname");
    private By lastName = By.id("lastname");
    private By email = By.id("email_address");
    private By password = By.id("password");
    private By confirmPassword = By.id("confirmation"); // <-- FIXED ID
    private By registerButton = By.cssSelector("button[title='Register']");
    private By successMessage = By.className("success-msg");

    public void fillForm(String fname, String lname, String emailAddr, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(fname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).sendKeys(lname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).sendKeys(emailAddr);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword)).sendKeys(pass);
    }

    public void submitForm() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center', inline: 'nearest' });", driver.findElement(registerButton));

        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).findElement(By.tagName("span")).getText();
    }
}


