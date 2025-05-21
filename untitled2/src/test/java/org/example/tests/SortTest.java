package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.AllProductPage;
import org.example.pages.SortWishlistPage;
import org.example.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SortTest extends BaseTest {

    @Test
    public void testProductsAreSortedByPriceLowToHighAndWishlistCount() {
        String email = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();

        HomePage home = new HomePage(driver);
        home.openAccountMenu();
        home.signIn();

        new LoginPage(driver).login(email, password);

        SortWishlistPage wishlistPage = new SortWishlistPage(driver);
        home.goToAllWomanProducts();
        wishlistPage.sortByPrice();

        AllProductPage allProductPage = new AllProductPage(driver);
        List<Double> prices = allProductPage.getAllDisplayedProductPrices();



        wishlistPage.addFirstTwoProductsToWishlist();

        wishlistPage.openWishlistFromAccount();
        String wishlistHeader = wishlistPage.getWishlistHeader();
        System.out.println("Wishlist header: " + wishlistHeader);

        int itemCount = wishlistPage.getWishlistItemCount();
        Assert.assertEquals(itemCount, 2, "Numri i produkteve në wishlist nuk është 2");

        home.openAccountMenu();
        home.logout();
    }
}
