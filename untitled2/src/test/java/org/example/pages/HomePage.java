package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class HomePage {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait=new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    private By accountButton = By.cssSelector("div.account-cart-wrapper a.skip-account");
    private By loginLink = By.cssSelector("[title='Log In']");
    private By registerLink = By.linkText("Register");
    private By logoutLink = By.linkText("Log Out");

    public void openAccountMenu() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(accountButton));
        driver.findElement(accountButton).click();
    }

    public void signIn() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(loginLink));
        driver.findElement(loginLink).click();

    }


    public void clickRegisterLink() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(registerLink));
        driver.findElement(registerLink).click();
    }

    public void logout() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
        driver.findElement(logoutLink).click();
    }
    By myWishlistLink = By.xpath("//a[contains(@title, 'My Wishlist')]");

    public void clickMyWishlist() {
        openAccountMenu();

        webDriverWait.until(ExpectedConditions.elementToBeClickable(myWishlistLink));

        driver.findElement(myWishlistLink).click();
    }


    private By womanMenu = By.xpath("//a[text()='Women']");
    private By viewAllWomanLink = By.xpath("//a[text()='View All Women']");

    public void goToAllWomanProducts() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(womanMenu));
        Actions actions = new Actions(driver);
        WebElement womenMenuElement = driver.findElement(womanMenu);
        WebElement viewAllLinkElement = driver.findElement(viewAllWomanLink);

        actions.moveToElement(womenMenuElement).pause(500).moveToElement(viewAllLinkElement).click().build().perform();
    }


}
