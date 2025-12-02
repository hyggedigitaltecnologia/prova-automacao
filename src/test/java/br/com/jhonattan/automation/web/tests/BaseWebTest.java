package br.com.jhonattan.automation.web.tests;

import br.com.jhonattan.automation.core.web.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseWebTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
    }

    @AfterEach
    public void teardown() {
        DriverFactory.quitDriver();
    }
}
