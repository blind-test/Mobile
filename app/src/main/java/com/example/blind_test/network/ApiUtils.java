package com.example.blind_test.network;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://blind-test-api.herokuapp.com";

    public static Api getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(Api.class);
    }

}
