package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SortWishlistPage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    public SortWishlistPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }


    private By sortByDropdown = By.cssSelector("select[title='Sort By']");

    private By products = By.cssSelector("ul.products-grid li.item");
    private By addToWishlist = By.cssSelector(".link-wishlist");

    private By accountWishlistLink = By.cssSelector("a[title='My Wishlist']");
    private By wishlistHeader = By.cssSelector("div.page-title h1");

    private void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center', inline: 'nearest' });", element);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }



    public void sortByPrice() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortByDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Price");
    }


    public void addFirstTwoProductsToWishlist() {
        int added = 0;
        int index = 0;

        while (added < 2) {
            List<WebElement> productList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(products));

            if (productList.size() <= index) {
                System.out.println("Nuk ka më produkte për të shtuar në wishlist.");
                break;
            }

            try {
                WebElement product = productList.get(index);

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
                actions.moveToElement(product).perform();

                WebElement wishlistBtn = wait.until(ExpectedConditions.elementToBeClickable(product.findElement(addToWishlist)));
                wishlistBtn.click();

                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fancybox-overlay")));

                added++;
                index++;

            } catch (StaleElementReferenceException e) {
                System.out.println("Element stale, rifreskoj listën dhe provoj sërish për produktin " + index);
            } catch (Exception e) {
                System.out.println("Gabim duke shtuar në wishlist: " + e.getMessage());
                index++;
            }
        }

        System.out.println("U shtuan gjithsej " + added + " produkte në wishlist.");
    }






    public int getWishlistItemCount() {
        String headerText = driver.findElement(wishlistHeader).getText();
        String digitsOnly = headerText.replaceAll("[^0-9]", "");
        return Integer.parseInt(digitsOnly);
    }

    public void openWishlistFromAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(accountWishlistLink)).click();
    }

    public String getWishlistHeader() {
        return driver.findElement(wishlistHeader).getText();
    }
}