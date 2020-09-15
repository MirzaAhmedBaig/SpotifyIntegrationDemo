package com.mab.spotifyIntegrationdemo.network

import com.mab.spotifyIntegrationdemo.SpotifyCredentials
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyApisManager {

    @FormUrlEncoded
    @POST(ACCESS_TOKEN_PATH)
    fun getAccessToken(
        @Field("grant_type") grant_type: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUrl: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<SpotifyCredentials>

    @FormUrlEncoded
    @POST(ACCESS_TOKEN_PATH)
    fun getRefreshToken(
        @Field("grant_type") grant_type: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<SpotifyCredentials>
}