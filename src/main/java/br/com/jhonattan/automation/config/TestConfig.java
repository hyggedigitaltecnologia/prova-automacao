package br.com.jhonattan.automation.config;

public final class TestConfig {

    private TestConfig() {}

    public static String getRestCountriesBaseUrl() {
        return System.getProperty("restcountries.baseUrl", "https://restcountries.com");
    }

    public static String getJsonPlaceholderBaseUrl() {
        return System.getProperty("jsonplaceholder.baseUrl", "https://jsonplaceholder.typicode.com");
    }

    public static long getApiTimeoutMs() {
        return Long.parseLong(System.getProperty("api.timeout.ms", "5000"));
    }

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }

    public static long getWebExplicitWaitSeconds() {
        return Long.parseLong(System.getProperty("web.explicit.wait.seconds", "10"));
    }
}
