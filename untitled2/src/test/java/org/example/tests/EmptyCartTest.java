package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.utils.CredentialsUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class EmptyCartTest extends BaseTest {

    @Test
    public void testEmptyCartFunctionality() {
        String registeredEmail = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        HomePage home = new HomePage(driver);
        home.openAccountMenu();
        home.signIn();

        new LoginPage(driver).login(registeredEmail, password);

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        List<WebElement> removeButtons = cartPage.getRemoveButtons();

        for (int i = removeButtons.size() - 1; i >= 0; i--) {
            WebElement button = removeButtons.get(i);
            button.click();

            try {
                driver.switchTo().alert().accept();
            } catch (Exception ignored) {}

            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.stalenessOf(button));
        }

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart is not empty after removing all items.");
    }
}
