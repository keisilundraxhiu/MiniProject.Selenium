package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.example.utils.CredentialsUtil;

public class RegisterTest extends BaseTest {

    @BeforeMethod
    public void disableLogin() {
        loginRequired = false;
    }

    @Test
    public void testUserRegistration() {
        home = new HomePage(driver);
        String email = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        home.openAccountMenu();
        home.clickRegisterLink();
        wait.until(ExpectedConditions.titleContains("Create New Customer Account"));

        Assert.assertTrue(driver.getTitle().contains("Create New Customer Account"), "Page title is not correct.");

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillForm("user","keisi",email,password);
        registerPage.submitForm();

        Assert.assertTrue(registerPage.isSuccessMessageDisplayed(), "Success message not displayed");
        Assert.assertTrue(registerPage.getSuccessMessage().contains("Thank you for registering with Tealium Ecommerce."), "Unexpected success message");

        home.openAccountMenu();
        home.logout();
    }
}
