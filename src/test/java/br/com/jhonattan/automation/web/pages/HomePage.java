package br.com.jhonattan.automation.web.pages;

import br.com.jhonattan.automation.web.core.BasePage;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private static final String URL = "https://www.netshoes.com.br/";

    private final By searchInput = By.cssSelector("input[type='search'], input[name='search']");

    public void open() {
        driver.get(URL);
    }

    public void search(String term) {
        type(searchInput, term);
        pressEnter(searchInput);
    }
}
