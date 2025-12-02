package br.com.jhonattan.automation.web.core;

import br.com.jhonattan.automation.core.web.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.NoSuchElementException;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        WebElement element = waitClickable(locator);
        scrollIntoView(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            jsClick(element);
        }
    }

    protected void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", element);
    }

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void pressEnter(By locator) {
        try {
            WebElement element = waitVisible(locator);

            try {
                element.click();
            } catch (Exception ignored) {
                try {
                    scrollIntoView(element);
                } catch (Exception ignore) { }
            }

            try {
                element.sendKeys(Keys.ENTER);
            } catch (InvalidElementStateException | StaleElementReferenceException ex) {
                new Actions(driver).moveToElement(element).sendKeys(Keys.ENTER).perform();
            }

        } catch (TimeoutException e) {
            throw new IllegalStateException("Elemento não visível para enviar ENTER: " + locator, e);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao pressionar ENTER no elemento: " + locator, e);
        }
    }

    protected boolean isPresent(By locator) {
        try {
            var elements = driver.findElements(locator);
            if (elements == null || elements.isEmpty()) return false;
            for (WebElement el : elements) {
                try {
                    if (el.isDisplayed()) return true;
                } catch (StaleElementReferenceException ignored) { }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    protected FluentWait<WebDriver> fluentWait(Duration timeout, Duration pollInterval) {
        return new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(pollInterval)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    protected void retryClick(By locator, int attempts, Duration interval) {
        int tries = 0;
        while (tries < attempts) {
            try {
                click(locator);
                return;
            } catch (Exception e) {
                tries++;
                log.warn("retryClick tentativa {}/{} falhou para {}: {}", tries, attempts, locator, e.toString());
                try {
                    Thread.sleep(interval.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retryClick", ie);
                }
            }
        }
        throw new IllegalStateException("Não foi possível clicar no elemento após "
                + attempts + " tentativas: " + locator);
    }

    protected String safeGetText(By locator) {
        try {
            var elements = driver.findElements(locator);
            if (elements == null || elements.isEmpty()) return "";
            for (WebElement el : elements) {
                try {
                    if (el.isDisplayed()) return el.getText();
                } catch (StaleElementReferenceException ignored) { }
            }
            return "";
        } catch (Exception e) {
            log.debug("safeGetText erro para {}: {}", locator, e.toString());
            return "";
        }
    }

    protected void waitForJsAndJQuery(Duration timeout) {
        FluentWait<WebDriver> f = fluentWait(timeout, Duration.ofMillis(500));
        try {
            f.until(driver1 -> {
                try {
                    Boolean jsReady = (Boolean) js.executeScript("return document.readyState === 'complete'");
                    if (jsReady == null || !jsReady) return false;
                    Boolean jQueryFinished = (Boolean) js.executeScript(
                            "return (typeof jQuery !== 'undefined') ? (jQuery.active === 0) : true");
                    return jQueryFinished != null && jQueryFinished;
                } catch (Exception e) {
                    return true; // se der erro, não trava
                }
            });
        } catch (Exception e) {
            log.warn("waitForJsAndJQuery timeout: {}", e.toString());
        }
    }
}
