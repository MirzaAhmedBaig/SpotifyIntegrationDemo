package com.mab.spotifyIntegrationdemo.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyClient {
    var retrofit2: Retrofit? = null
        private set
    private var spotifyApisManager: SpotifyApisManager? = null

    val spotifyClientAPI: SpotifyApisManager
        get() {
            if (spotifyApisManager == null) {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                retrofit2 = Retrofit.Builder()
                    .baseUrl(SPOTIFY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                spotifyApisManager = retrofit2?.create(SpotifyApisManager::class.java)
            }
            return spotifyApisManager!!
        }
}