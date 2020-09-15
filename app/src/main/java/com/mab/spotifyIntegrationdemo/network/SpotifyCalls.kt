package com.mab.spotifyIntegrationdemo.network

import android.content.Context
import com.mab.spotifyIntegrationdemo.SpotifyCredentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SpotifyCalls {

    val FAILED_CODE = -500


    fun getAccessToken(
        credentials: SpotifyCredentials,
        onSuccess: (SpotifyCredentials) -> Unit,
        onFailed: (code: Int) -> Unit
    ) {
        val call = SpotifyClient.spotifyClientAPI.getAccessToken(
            code = credentials.code!!,
            redirectUrl = credentials.redirect_uri!!,
            clientId = credentials.client_id!!,
            clientSecret = credentials.client_secret!!
        )
        call.enqueue(object : Callback<SpotifyCredentials> {
            override fun onFailure(call: Call<SpotifyCredentials>, t: Throwable) {
                t.printStackTrace()
                onFailed(FAILED_CODE)
            }

            override fun onResponse(
                call: Call<SpotifyCredentials>,
                response: Response<SpotifyCredentials>
            ) {
                response.body()?.let {
                    //save and return saved on
                    it.access_token
                    it.token_type
                    it.expires_in
                    it.createdAt = System.currentTimeMillis()
                    it.refresh_token //if not null
                    it.scope
                    onSuccess(it)
                } ?: onFailed(response.code())
            }

        })
    }

    fun getRefreshToken(
        credentials: SpotifyCredentials,
        onSuccess: (SpotifyCredentials) -> Unit,
        onFailed: (code: Int) -> Unit
    ) {
        val call = SpotifyClient.spotifyClientAPI.getRefreshToken(
            refreshToken = credentials.refresh_token!!,
            clientId = credentials.client_id!!,
            clientSecret = credentials.client_secret!!
        )
        call.enqueue(object : Callback<SpotifyCredentials> {
            override fun onFailure(call: Call<SpotifyCredentials>, t: Throwable) {
                t.printStackTrace()
                onFailed(FAILED_CODE)
            }

            override fun onResponse(
                call: Call<SpotifyCredentials>,
                response: Response<SpotifyCredentials>
            ) {
                response.body()?.let {
                    //save and return saved on
                    it.access_token
                    it.token_type
                    it.expires_in
                    it.createdAt = System.currentTimeMillis()
                    it.refresh_token //if not null
                    it.scope
                    onSuccess(it)
                } ?: onFailed(response.code())
            }

        })
    }
}