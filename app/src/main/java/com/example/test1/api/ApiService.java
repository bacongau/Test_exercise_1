package com.example.test1.api;

import com.example.test1.model.response.LoginResponse;
import com.example.test1.model.response.ProfileResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {


    @POST("api/auth/students/login")
    Single<LoginResponse> login(@Header("Authorization") String encodedString);

    @GET("api/students/profile")
    Single<ProfileResponse> getProfile(@Header("Authorization") String bearerToken);

    //  https://22e76fe59263.ngrok.io/api/students/personalInfo?firstName=Tạ&lastName=Tân Cương&gender=MALE&email=cuong.tt@gmail.com1&phone=0123456789&birthday=02022020&identityCard=0132461
    @Multipart
    @PUT("api/students/personalInfo")
    Single<ProfileResponse> updateProfile(@Header("Authorization") String bearerToken,
                                          @Query("firstName") String firstName,
                                          @Query("lastName") String lastName,
                                          @Query("gender") String gender,
                                          @Query("email") String email,
                                          @Query("phone") String phone,
                                          @Query("birthday") long birthday,
                                          @Query("identityCard") String identityCard,
                                          @Part MultipartBody.Part fileImage);
}
