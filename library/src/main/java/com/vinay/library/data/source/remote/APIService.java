package com.vinay.library.data.source.remote;


import com.vinay.library.data.user.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 28-03-2018.
 */

public interface APIService {

    @POST("{endpoint}")
    Call<User> login(
            @Path("endpoint") String endpoint,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body Map<String, Object> body
    );


    @GET("{endpoint}")
    Call<User> userDetails(
            @Path("endpoint") String endpoint,
            @Header("Authorization") String credentials,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Query("id") int providerId);
}
