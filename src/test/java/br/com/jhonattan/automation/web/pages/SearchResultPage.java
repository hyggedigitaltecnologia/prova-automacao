package br.com.jhonattan.automation.web.pages;

import br.com.jhonattan.automation.web.core.BasePage;
import org.openqa.selenium.By;

public class SearchResultPage extends BasePage {

    private final By firstProductLink =
            By.xpath("(//span[contains(@class,'card__description--name')])[1]/ancestor::a[1]");

    public void openFirstProduct() {
        click(firstProductLink);
    }
}
