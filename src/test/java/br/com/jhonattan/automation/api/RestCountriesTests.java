package br.com.jhonattan.automation.api;

import br.com.jhonattan.automation.config.TestConfig;
import br.com.jhonattan.automation.core.api.RestCountriesClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("api")
@Epic("API")
@Feature("RESTCountries")
public class RestCountriesTests {

    private final RestCountriesClient client = new RestCountriesClient();

    @Test
    @Story("GET /v3.1/name/canada")
    @DisplayName("Deve retornar dados válidos para o país Canada")
    void shouldReturnValidCountryForCanada() {
        Response response = client.getCountryByName("canada");

        assertEquals(200, response.statusCode());
        assertTrue(response.time() < TestConfig.getApiTimeoutMs());

        var countries = response.jsonPath().getList("$");
        assertNotNull(countries);
        assertFalse(countries.isEmpty());

        String nameCommon = response.jsonPath().getString("[0].name.common");
        assertEquals("Canada", nameCommon);
    }
}
