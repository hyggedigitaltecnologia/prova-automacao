package br.com.jhonattan.automation.core.api;

public final class ApiEndpoints {

    private ApiEndpoints() {}

    public static final class RestCountries {
        private RestCountries() {}
        public static final String GET_COUNTRY_BY_NAME = "/v3.1/name/{name}";
    }

    public static final class JsonPlaceholder {
        private JsonPlaceholder() {}
        public static final String CREATE_POST = "/posts";
    }
}
