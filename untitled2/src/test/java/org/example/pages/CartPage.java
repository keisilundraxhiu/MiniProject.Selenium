package org.example.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class CartPage {
    private WebDriver driver;


    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By wishlistItems = By.cssSelector(".data-table.wishlist-table tbody tr");
    private By addToCartBtn = By.cssSelector(".btn-cart");

    private By sizeSelect = By.id("attribute92");
    private By colorSelect = By.id("attribute180");
    private By addToCartDetailBtn = By.cssSelector("button.btn-cart");

    private By cartQtyField = By.cssSelector("input.qty");
    private By updateButton = By.cssSelector("button[title='Update']");
    private By grandTotal = By.cssSelector("strong .price");

    private By cartLink = By.linkText("Cart");

    public void addAllWishlistItemsToCart() {
        List<WebElement> rows = driver.findElements(wishlistItems);
        for (WebElement row : rows) {
            WebElement addBtn = row.findElement(addToCartBtn);
            addBtn.click();


            if (isElementPresent(sizeSelect)) {
                new Select(driver.findElement(sizeSelect)).selectByIndex(1);
            }
            if (isElementPresent(colorSelect)) {
                new Select(driver.findElement(colorSelect)).selectByIndex(1);
            }

            driver.findElement(addToCartDetailBtn).click();
        }
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }

    public void changeQtyOfFirstItemAndUpdate(int quantity) {
        List<WebElement> qtyFields = driver.findElements(cartQtyField);
        if (!qtyFields.isEmpty()) {
            WebElement firstQty = qtyFields.get(0);
            firstQty.clear();
            firstQty.sendKeys(String.valueOf(quantity));
            driver.findElement(updateButton).click();
        }
    }


    private By itemSubtotal = By.cssSelector("td.product-cart-total .price");

    public double getSumOfItemSubtotals() {
        List<WebElement> prices = driver.findElements(itemSubtotal);
        double total = 0.0;
        for (WebElement price : prices) {
            String amount = price.getText().replace("$", "").trim();
            total += Double.parseDouble(amount);
        }
        return total;
    }


    public double getGrandTotal() {
        String totalText = driver.findElement(grandTotal).getText().replace("$", "").trim();
        return Double.parseDouble(totalText);
    }

    private boolean isElementPresent(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    private By cartRows = By.cssSelector("table#shopping-cart-table tbody tr");
    private By deleteButtons = By.cssSelector("a[title='Remove Item']");
    private By emptyMessage = By.cssSelector(".cart-empty p");

    public int getNumberOfCartItems() {
        return driver.findElements(cartRows).size();
    }

    public void deleteFirstItem() {
        List<WebElement> deleteLinks = driver.findElements(deleteButtons);
        if (!deleteLinks.isEmpty()) {
            deleteLinks.get(0).click();
            driver.switchTo().alert().accept();
        }
    }

    public boolean isCartEmptyMessageVisible() {
        try {
            return driver.findElement(emptyMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> getRemoveButtons() {
        return driver.findElements(deleteButtons);
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartRows).isEmpty() || isCartEmptyMessageVisible();
    }

}
