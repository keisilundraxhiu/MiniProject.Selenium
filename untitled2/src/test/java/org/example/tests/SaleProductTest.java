package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.AllProductPage;
import org.example.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SaleProductTest extends BaseTest {

    @Test
    public void testSaleProductsStyle() {
        String registeredEmail = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        HomePage home = new HomePage(driver);
        home.openAccountMenu();
        home.signIn();

        new LoginPage(driver).login(registeredEmail, password);

        AllProductPage gridPage = new AllProductPage(driver);
        gridPage.goToAllSaleProducts();


        boolean allProductsStyledCorrectly = gridPage.verifyPricesForAllProducts();
        Assert.assertTrue(allProductsStyledCorrectly, "Some products do not have correct sale price styling.");
    }
}
