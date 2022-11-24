package com.example.test2;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    public static final String API_URL = "https://ceb2-112-186-87-153.jp.ngrok.io";

    @GET("tests")
    Call<ResponseBody> get_Test5();

    @GET("tests")
    Call<List<Json_Test>> get_Test3();

    @GET("/accounts/")
    Call<List<UserData>> get_account();

    @POST("/account/")
    Call<Data> post_account(@Body UserData user);

    @POST("/login/")
    Call<Data> post_login(@Body UserData user);

    @POST("/ticket_post/")
    Call<Data> post_ticket(@Body TicketData data);

    @POST("/ticket_get/")
    Call<TicketData> get_ticket(@Body TicketData data);

    @POST("tests")
    Call<ResponseBody> post_Test4(@Part("email") RequestBody email);

    @POST("/tests/")
    Call<Json_Test> post_Test3(@Body Json_Test item);

    @GET("tests/{pk}/")
    Call<Json_Test> get_Test4(@Path("pk") int pk);

    @GET("tests")
    Call<ResponseBody> get_Test(@Query("format") String json);

    @FormUrlEncoded
    @POST("tests")
    Call<Json_Test> post_Test(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @POST("tests")
    Call<ResponseBody> post_Test2(@Field("email") String test);

    @FormUrlEncoded
    @PATCH("tests/{pk}/")
    Call<ResponseBody> patch_Test(@Path("pk") int pk, @Query("format") String json, @Field("email") String test);

    @DELETE("tests/{pk}/")
    Call<ResponseBody> delete_Patch_Test(@Path("pk") int pk, @Query("format") String json);
}
