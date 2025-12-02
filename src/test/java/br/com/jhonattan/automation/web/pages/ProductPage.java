package br.com.jhonattan.automation.web.pages;

import br.com.jhonattan.automation.web.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {

    private final By buyButton = By.cssSelector(
            "button.add-cart-button, " +
                    "button[data-testid='buy-button'], " +
                    "button[id*='btn-buy']"
    );

    private final By sizeListContainer = By.cssSelector("ul.size-list.radio-options");

    private final By availableSizes = By.cssSelector(
            "ul.size-list.radio-options li.size-list__item.size:not(.unavailable) button.size__link"
    );

    private void selectFirstAvailableSizeIfNeeded() {
        if (!isPresent(sizeListContainer)) {
            log.info("Produto não possui grade de tamanhos visível. Seguindo sem seleção de tamanho.");
            return;
        }

        List<WebElement> options = driver.findElements(availableSizes);

        if (options == null || options.isEmpty()) {
            throw new IllegalStateException("Nenhum tamanho disponível para seleção no produto.");
        }

        WebElement firstAvailable = options.get(0);
        scrollIntoView(firstAvailable);

        try {
            if (firstAvailable.isEnabled()) {
                firstAvailable.click();
            } else {
                jsClick(firstAvailable);
            }
            log.info("Selecionado tamanho disponível: {}", firstAvailable.getText().trim());
        } catch (Exception e) {
            log.warn("Falha ao clicar no tamanho via WebElement, tentando via JavaScript. Erro: {}", e.toString());
            jsClick(firstAvailable);
        }

        waitForJsAndJQuery(Duration.ofSeconds(5));
    }

    public void addToCart() {
        selectFirstAvailableSizeIfNeeded();
        click(buyButton);
    }
}
