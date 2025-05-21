package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.example.utils.CredentialsUtil;

public class LoginTest extends BaseTest {

    @Test
    public void testUserLogin() {
        String registeredEmail = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        HomePage home = new HomePage(driver);
        home.openAccountMenu();
        home.signIn();


        LoginPage login = new LoginPage(driver);
        login.login(registeredEmail, password);

        String headerText = login.getWelcomeMessage();
        System.out.println(headerText);
        Assert.assertTrue(headerText.equalsIgnoreCase("WELCOME, USER KEISI!"), "Login welcome message not found.");

        home.openAccountMenu();
        home.logout();
    }
}
