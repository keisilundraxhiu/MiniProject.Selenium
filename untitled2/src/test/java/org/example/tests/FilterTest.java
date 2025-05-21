package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.AllProductPage;
import org.example.utils.CredentialsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class FilterTest extends BaseTest {


    @Test
    public void testApplyFilterForMen() {
        HomePage homePage = new HomePage(driver);
        homePage.openAccountMenu();
        homePage.signIn();

        LoginPage loginPage = new LoginPage(driver);
        String registeredEmail = CredentialsUtil.getEmail();
        String password = CredentialsUtil.getPassword();
        loginPage.login(registeredEmail, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Men']")));

        AllProductPage allProductPage = new AllProductPage(driver);
        allProductPage.goToAllMenProducts();
        wait.until(ExpectedConditions.urlContains("men"));

        allProductPage.applyFilter("color", "Black");
        Assert.assertTrue(allProductPage.isColorFilterApplied("Black"), "Filtri i ngjyrës nuk është aplikuar saktë.");
        allProductPage.scrollToFirstProduct();
        allProductPage.applyFilter("size", "M");
        allProductPage.applyFilter("price", "$00.00 - $99.99");

        Assert.assertTrue(allProductPage.verifyPriceRangeAndCount(00.00, 99.99, 3),
                "Filtrimi sipas çmimit ose numri i produkteve nuk përputhet.");
    }


}