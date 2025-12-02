package br.com.jhonattan.automation.core.api;

import io.restassured.response.Response;

import static br.com.jhonattan.automation.core.api.ApiEndpoints.RestCountries.GET_COUNTRY_BY_NAME;
import static io.restassured.RestAssured.given;

public class RestCountriesClient extends ApiClient {

    public Response getCountryByName(String countryName) {
        return given()
                .spec(restCountriesSpec())
                .basePath(GET_COUNTRY_BY_NAME)
                .pathParam("name", countryName)
                .when()
                .get();
    }
}
