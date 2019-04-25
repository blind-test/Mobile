package com.example.blind_test.network;

import com.example.blind_test.model.PostAuth;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Call<PostAuth> coPost(@Field("email") String email,
                            @Field("password") String password);
}
