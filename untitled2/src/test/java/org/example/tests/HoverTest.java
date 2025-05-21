package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.AllProductPage;
import org.example.utils.CredentialsUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HoverTest extends BaseTest {

    @Test
    public void testHoverChangesBorderColor() {
        HomePage home = new HomePage(driver);
        home.openAccountMenu();

        home.signIn();

        String email = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        new LoginPage(driver).login(email, password);
        home.goToAllWomanProducts();

        AllProductPage gridPage = new AllProductPage(driver);
        AllProductPage allProductPage = new AllProductPage(driver);
        allProductPage.scrollToFirstProduct();

        String[] colors = gridPage.getBorderColorBeforeAndAfterHover();
        String before = colors[0];
        String after = colors[1];

        System.out.println("Before hover border-color: " + before);
        System.out.println("After hover border-color: " + after);

        Assert.assertNotEquals(after, before, "Border color did not change on hover.");
    }
}
