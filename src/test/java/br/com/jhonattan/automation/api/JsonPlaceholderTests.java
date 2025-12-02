package br.com.jhonattan.automation.api;

import br.com.jhonattan.automation.config.TestConfig;
import br.com.jhonattan.automation.core.api.JsonPlaceholderClient;
import br.com.jhonattan.automation.model.jsonplaceholder.PostPayload;
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
@Feature("JSONPlaceholder")
public class JsonPlaceholderTests {

    private final JsonPlaceholderClient client = new JsonPlaceholderClient();

    @Test
    @Story("POST /posts")
    @DisplayName("Deve criar um novo post com sucesso")
    void shouldCreateNewPost() {
        PostPayload payload = new PostPayload("foo", "bar", 1);

        Response response = client.createPost(payload);

        assertEquals(201, response.statusCode());
        assertTrue(response.time() < TestConfig.getApiTimeoutMs());

        Integer id = response.jsonPath().getInt("id");
        assertNotNull(id);
        assertTrue(id > 0);

        assertEquals(payload.title(), response.jsonPath().getString("title"));
        assertEquals(payload.body(), response.jsonPath().getString("body"));
        assertEquals(payload.userId(), response.jsonPath().getInt("userId"));
    }
}
