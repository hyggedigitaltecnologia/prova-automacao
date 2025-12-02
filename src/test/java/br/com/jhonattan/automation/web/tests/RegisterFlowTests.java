package br.com.jhonattan.automation.web.tests;

import br.com.jhonattan.automation.web.pages.CartPage;
import br.com.jhonattan.automation.web.pages.HomePage;
import br.com.jhonattan.automation.web.pages.ProductPage;
import br.com.jhonattan.automation.web.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("web")
@Epic("Web")
@Feature("Carrinho Netshoes")
public class RegisterFlowTests extends BaseWebTest {

    @Test
    @Story("Adicionar ao carrinho")
    @DisplayName("Deve buscar um produto e adicioná-lo ao carrinho")
    void shouldSearchProductAndAddToCart() {
        HomePage homePage = new HomePage();
        homePage.open();
        homePage.search("Tênis");

        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.openFirstProduct();

        ProductPage productPage = new ProductPage();
        productPage.addToCart();

        CartPage cartPage = new CartPage();
        cartPage.openFromMiniCart();

        assertTrue(cartPage.isAnyItemInCart(),
                "Deveria existir ao menos um item no carrinho.");
    }

    @Test
    @Story("Remover do carrinho")
    @DisplayName("Deve remover o produto do carrinho e exibir mensagem de carrinho vazio")
    void shouldRemoveProductFromCart() {
        HomePage homePage = new HomePage();
        homePage.open();
        homePage.search("Tênis");

        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.openFirstProduct();

        ProductPage productPage = new ProductPage();
        productPage.addToCart();

        CartPage cartPage = new CartPage();
        cartPage.openFromMiniCart();

        assertTrue(cartPage.isAnyItemInCart(),
                "Pré-condição: carrinho deve ter pelo menos um item antes da remoção.");

        cartPage.removeFirstItem();

        assertTrue(cartPage.isCartEmpty(),
                "Após remover o produto, o carrinho deveria estar vazio.");
    }
}
