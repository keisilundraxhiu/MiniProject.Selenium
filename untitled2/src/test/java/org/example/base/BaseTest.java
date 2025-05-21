package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected HomePage home;
    protected boolean loginRequired = true;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://ecommerce.tealiumdemo.com");
        handleConsentPopup();
    }

    public void handleConsentPopup() {
        try {
            WebElement optInRadio = wait.until(ExpectedConditions.elementToBeClickable(By.id("privacy_pref_optin")));
            optInRadio.click();
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("consent_prompt_submit")));
            submitButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("privacy_pref_optin")));
            System.out.println("Consent popup handled.");
        } catch (Exception e) {
            System.out.println("Consent popup not present.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess()) {
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public void takeScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get("screenshots", testName + ".png");
            Files.createDirectories(dest.getParent());
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + dest.toString());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
