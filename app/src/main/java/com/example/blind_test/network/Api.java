package com.example.blind_test.network;

import com.example.blind_test.fragments.User;
import com.example.blind_test.model.Lobbies;
import com.example.blind_test.model.PostAuth;
import com.example.blind_test.model.PostCo;
import com.example.blind_test.model.Score;
import com.example.blind_test.model.Socket;
import com.example.blind_test.model.Theme;
import com.example.blind_test.model.listUsers;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @Headers("source:android")
    @POST("/auth/sign_up")
    @FormUrlEncoded
    Call<PostAuth> inscriPost(@Field("email") String email,
                            @Field("password") String password,
                            @Field("password_confirmation") String password_confirmation,
                            @Field("nickname") String nickname);

    @Headers("source:android")
    @POST("/lobbies/{id}/join")
    Call<Socket> joinMessage(@HeaderMap Map<String, String> headers,
                                   @Path("id") String id);

    @Headers("source:android")
    @POST("/lobbies/{id}/game")
    Call<Socket> joinGame(@HeaderMap Map<String, String> headers,
                          @Path("id") String id);

    @Headers("source:android")
    @POST("/auth/sign_in")
    @FormUrlEncoded
    Call<PostCo> coPost(@Field("email") String email,
                        @Field("password") String password);


    @Headers("source:android")
    @DELETE("/auth/sign_out")
    Call<PostCo> outDelete(@HeaderMap Map<String, String> headers);

    @Headers("source:android")
    @GET("/users")
    Call<List<listUsers>> listUserGET(@HeaderMap Map<String, String> headers,
                                      @Query("q") String nickname);

    @Headers("source:android")
    @GET("/lobbies")
    Call<List<Lobbies>> listLobbies(@HeaderMap Map<String, String> headers);

    @Headers("source:android")
    @POST("/friendships")
    @FormUrlEncoded
    Call<listUsers> askFriend(@HeaderMap Map<String, String> headers,
                              @Field("asked_to") int id);

    @Headers("source:android")
    @GET("/friendships")
    Call<List<listUsers>> listUserStatus(@HeaderMap Map<String, String> headers,
                                      @Query("status") String status);

    @Headers("source:android")
    @GET("/lobbies/{id}/scores")
    Call<List<Score>> listScoreGeneral(@HeaderMap Map<String, String> headers,
                                       @Path("id") String id);

    @Headers("source:android")
    @GET("/lobbies/{id}/scores?daily=true")
    Call<List<Score>> listScoreGeneralDay(@HeaderMap Map<String, String> headers,
                                       @Path("id") String id);

    @Headers("source:android")
    @PUT("/friendships/{id}")
    Call<listUsers> responsFriend(@HeaderMap Map<String, String> headers,
                                  @Path("id") String id,
                                  @Query("status") String status);

    @Headers("source:android")
    @PUT("/users/{id}")
    Call<listUsers> updateUser(@HeaderMap Map<String, String> headers,
                                  @Path("id") String id,
                                  @Query("nickname") String nickname,
                                  @Query("email") String email);

    @Headers("source:android")
    @GET("/users/{id}")
    Call<listUsers> GetUser(@HeaderMap Map<String, String> headers,
                       @Path("id") String id);

    @Headers("source:android")
    @GET("/themes?public=true")
    Call<List<Theme>> GetThemes(@HeaderMap Map<String, String> headers);

    @Headers("source:android")
    @POST("/lobbies")
    @FormUrlEncoded
    Call<Lobbies> CreateLobbie(@HeaderMap Map<String, String> headers,
                              @Field("theme_id") String theme_id,
                               @Field("questions") String questions,
                               @Field("restricted") String restricted);
}
