package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {


    @Test
    public void testShoppingCart() {
        String registeredEmail = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        HomePage home = new HomePage(driver);
        home.openAccountMenu();
        home.signIn();


        new LoginPage(driver).login(registeredEmail, password);
        home.openAccountMenu();
        home.clickMyWishlist();

        CartPage cartPage = new CartPage(driver);
        cartPage.addAllWishlistItemsToCart();
        cartPage.goToCart();
        cartPage.getNumberOfCartItems();
        cartPage.deleteFirstItem();

        cartPage.changeQtyOfFirstItemAndUpdate(2);

        double expectedTotal = cartPage.getSumOfItemSubtotals();
        double actualTotal = cartPage.getGrandTotal();

        Assert.assertEquals(actualTotal, expectedTotal, 0.1, "Grand total does not match sum of item subtotals.");
    }
}