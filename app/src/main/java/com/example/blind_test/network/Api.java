package com.example.blind_test.network;

import com.example.blind_test.model.PostAuth;
import com.example.blind_test.model.PostCo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @POST("/auth/sign_up")
    @FormUrlEncoded
    Call<PostAuth> inscriPost(@Field("email") String email,
                            @Field("password") String password,
                            @Field("password_confirmation") String password_confirmation,
                            @Field("nickname") String nickname);

    @POST("/auth/sign_in")
    @FormUrlEncoded
    Call<PostCo> coPost(@Field("email") String email,
                        @Field("password") String password);


    @DELETE("/auth/sign_out")
    Call<PostCo> outDelete(@HeaderMap Map<String, String> headers);

}
