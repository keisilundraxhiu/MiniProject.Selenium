package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AllProductPage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    public AllProductPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void scrollToFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({ behavior: 'auto', block: 'center' });", first
        );

        wait.until(ExpectedConditions.elementToBeClickable(first));
    }


    private By products = By.cssSelector("ul.products-grid li.item");
    private By firstProduct = By.cssSelector("ul.products-grid li.item:first-child");

    private By menMenu = By.xpath("//a[text()='Men']");
    private By viewAllMen = By.xpath("//li[contains(@class,'level1')]//a[text()='View All Men']");


    private By saleMenu = By.xpath("//a[text()='Sale']");
    private By viewAllSale = By.xpath("//a[text()='View All Sale']");

    private By oldPrice = By.cssSelector(".regular-price .price");
    private By specialPrice = By.cssSelector(".special-price .price");


    public String[] getBorderColorBeforeAndAfterHover() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("ul.products-grid li.item:first-child a.product-image")
        ));

        String beforeHover = productLink.getCssValue("border-color");
        System.out.println("Border para hover: " + beforeHover);

        actions.moveToElement(productLink).perform();

        wait.until(driver -> {
            String after = productLink.getCssValue("border-color");
            System.out.println("Gjate hover: " + after);
            return !after.equals(beforeHover);
        });

        String afterHover = productLink.getCssValue("border-color");
        System.out.println("Border pas hover: " + afterHover);

        return new String[]{beforeHover, afterHover};
    }




    public List<Double> getAllDisplayedProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".price, .price-sale"));
        List<Double> prices = new ArrayList<>();

        for (WebElement element : priceElements) {
            String priceText = element.getText().replaceAll("[^\\d.,]", "").replace(",", ".");
            try {
                prices.add(Double.parseDouble(priceText));
            } catch (NumberFormatException e) {
                System.out.println("Nuk u konvertua ne double: " + priceText);
            }
        }

        return prices;
    }


    public void goToAllMenProducts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement menMenuElement = wait.until(ExpectedConditions.visibilityOfElementLocated(menMenu));

        Actions actions = new Actions(driver);
        actions.moveToElement(menMenuElement).perform();

        WebElement viewAllElement = wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllMen));
        wait.until(ExpectedConditions.elementToBeClickable(viewAllElement));


        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewAllElement);

    }


    public void applyFilter(String filterType, String value) {
        String filterLocator = "";

        switch (filterType.toLowerCase()) {
            case "color":
                filterLocator = "//dt[text()='Color']/following-sibling::dd[1]//a[.//img[contains(translate(@title,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + value.toLowerCase() + "')]]";
                break;
            case "size":
                filterLocator = "//label[contains(@for,'size') and contains(text(),'" + value + "')]";
                break;
            case "price":
                WebElement dropdown = driver.findElement(By.cssSelector("select#attribute146"));
                dropdown.click();
                String priceOptionXpath = "//select[@id='attribute146']/option[contains(text(),'" + value + "')]";
                driver.findElement(By.xpath(priceOptionXpath)).click();
                return;
            default:
                throw new IllegalArgumentException("Unsupported filter type: " + filterType);
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        js.executeScript("arguments[0].scrollIntoView({ behavior: 'auto', block: 'center', inline: 'nearest' });", driver.findElement(By.xpath(filterLocator)));
        WebElement filterElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterLocator)));
        filterElement.click();
    }

    public boolean isColorFilterApplied(String colorName) {
        List<WebElement> productList = driver.findElements(products);
        for (WebElement product : productList) {
            try {
                WebElement colorSwatch = product.findElement(By.cssSelector(".option-black.is-media.filter-match.selected"));
                String value = colorSwatch.getAttribute("data-option-label");

                if (!value.equalsIgnoreCase(colorName)) {
                    System.out.println("Selected color not highlighted");
                    return false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Color swatch not found.");
                return false;
            }
        }
        return true;
    }

    public boolean verifyPriceRangeAndCount(double minPrice, double maxPrice, int expectedCount) {
        List<WebElement> productList = driver.findElements(products);
        if (productList.size() != expectedCount) {
            System.out.println("Expected " + expectedCount + " products, found: " + productList.size());
            return false;
        }

        for (WebElement product : productList) {
            String priceText = product.findElement(By.cssSelector(".price")).getText().replace("$", "").trim();
            double price = Double.parseDouble(priceText);
            if (price < minPrice || price > maxPrice) {
                System.out.println("Product price out of range: $" + price);
                return false;
            }
        }
        return true;
    }



    public void goToAllSaleProducts() {
        WebElement sale = driver.findElement(saleMenu);
        actions.moveToElement(sale).perform();

        WebElement viewAll = driver.findElement(viewAllSale);
        actions.moveToElement(viewAll).click().perform();
    }

    public boolean verifyPricesForAllProducts() {
        List<WebElement> productList = driver.findElements(products);

        for (WebElement product : productList) {
            if (!checkBothPricesVisible(product)) {
                System.out.println("Product missing old or special price.");
                return false;
            }

            WebElement oldPriceElement = product.findElement(oldPrice);
            WebElement newPriceElement = product.findElement(specialPrice);

            if (!checkPriceStyle(oldPriceElement, newPriceElement)) {
                System.out.println("Style validation failed.");
                return false;
            }
        }

        return true;
    }

    private boolean checkBothPricesVisible(WebElement product) {
        List<WebElement> oldPrices = product.findElements(oldPrice);
        List<WebElement> newPrices = product.findElements(specialPrice);

        return oldPrices.size() > 0 && newPrices.size() > 0;
    }

    private boolean checkPriceStyle(WebElement oldPriceElement, WebElement newPriceElement) {
        String oldColor = oldPriceElement.getCssValue("color");
        String oldTextDecoration = oldPriceElement.getCssValue("text-decoration");

        String newColor = newPriceElement.getCssValue("color");
        String newTextDecoration = newPriceElement.getCssValue("text-decoration");

        if (!oldTextDecoration.contains("line-through")) {
            System.out.println("Old price does not have line-through: " + oldTextDecoration);
            return false;
        }

        if (newTextDecoration.contains("line-through")) {
            System.out.println("New price should not have line-through: " + newTextDecoration);
            return false;
        }

        if (!isGray(oldColor)) {
            System.out.println("Old price color is not gray: " + oldColor);
            return false;
        }

        if (!isBlue(newColor)) {
            System.out.println("New price color is not blue: " + newColor);
            return false;
        }

        return true;
    }

    private boolean isGray(String rgba) {
        return rgba.contains("128, 128, 128") || rgba.contains("102, 102, 102");
    }

    private boolean isBlue(String rgba) {
        return rgba.contains("0, 0, 255") || rgba.contains("51, 51, 153");
    }


}