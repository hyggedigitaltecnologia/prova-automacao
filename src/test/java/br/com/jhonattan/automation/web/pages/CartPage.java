package br.com.jhonattan.automation.web.pages;

import br.com.jhonattan.automation.web.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By miniCartLink = By.cssSelector(
            "a.mini-cart__link[title='Mini Cart'], " +
                    "a.mini-cart__link[href*='/cart']"
    );

    private final By productItems = By.cssSelector(
            "div.product-item, [data-testid='product-item'], [data-testid='basket-item']"
    );

    private final By removeItemButton = By.cssSelector(
            "i.remove-icon[qa-auto='product-btn-remove'], " +
                    "i[qa-auto='product-btn-remove'].remove-icon"
    );

    private final By emptyCartTitle = By.xpath(
            "//h1[contains(@class,'empty-page__title') and " +
                    "contains(normalize-space(),'Seu carrinho está vazio')]"
    );

    public void openFromMiniCart() {
        click(miniCartLink);

        if (!isPresent(productItems)) {
            waitVisible(emptyCartTitle);
        }
    }

    public boolean isAnyItemInCart() {
        List<WebElement> items = driver.findElements(productItems);
        if (items == null || items.isEmpty()) {
            return false;
        }

        for (WebElement item : items) {
            try {
                if (item.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {

            }
        }
        return false;
    }

    public void removeFirstItem() {
        click(removeItemButton);
        waitVisible(emptyCartTitle);
    }

    public boolean isCartEmpty() {
        if (isPresent(emptyCartTitle)) {
            return true;
        }
        return !isAnyItemInCart();
    }
}
