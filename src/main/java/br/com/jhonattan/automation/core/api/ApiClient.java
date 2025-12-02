package br.com.jhonattan.automation.core.api;

import br.com.jhonattan.automation.config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

    private static boolean isLoggingEnabled() {
        return Boolean.parseBoolean(System.getProperty("api.log", "true"));
    }

    private RequestSpecBuilder baseBuilder(String baseUri) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addFilter(new AllureRestAssured());

        if (isLoggingEnabled()) {
            builder
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        }

        return builder;
    }

    protected RequestSpecification restCountriesSpec() {
        return baseBuilder(TestConfig.getRestCountriesBaseUrl()).build();
    }

    protected RequestSpecification jsonPlaceholderSpec() {
        return baseBuilder(TestConfig.getJsonPlaceholderBaseUrl()).build();
    }
}
