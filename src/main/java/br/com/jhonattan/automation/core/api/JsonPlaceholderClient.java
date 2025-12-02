package br.com.jhonattan.automation.core.api;

import br.com.jhonattan.automation.model.jsonplaceholder.PostPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static br.com.jhonattan.automation.core.api.ApiEndpoints.JsonPlaceholder.CREATE_POST;
import static io.restassured.RestAssured.given;

public class JsonPlaceholderClient extends ApiClient {

    public Response createPost(PostPayload payload) {
        return given()
                .spec(jsonPlaceholderSpec())
                .basePath(CREATE_POST)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post();
    }
}
