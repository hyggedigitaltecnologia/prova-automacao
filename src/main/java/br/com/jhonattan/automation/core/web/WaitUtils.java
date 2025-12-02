package br.com.jhonattan.automation.core.web;

import br.com.jhonattan.automation.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private WaitUtils() {}

    private static WebDriverWait newWait() {
        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(TestConfig.getWebExplicitWaitSeconds())
        );
    }

    public static WebElement waitForVisible(By locator) {
        return newWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {
        return newWait().until(ExpectedConditions.elementToBeClickable(locator));
    }
}
