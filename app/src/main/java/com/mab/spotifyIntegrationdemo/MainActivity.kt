package com.mab.spotifyIntegrationdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mab.spotifyIntegrationdemo.network.SpotifyCalls
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.SpotifyAppRemote
import kaaes.spotify.webapi.android.SpotifyApi
import kaaes.spotify.webapi.android.models.Album
import kaaes.spotify.webapi.android.models.TracksPager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val REQUEST_CODE = 1337

    private var mSpotifyAppRemote: SpotifyAppRemote? = null
    private val connectionParams by lazy {
        ConnectionParams.Builder(getString(R.string.SPOTIFY_CLIENT_ID))
            .setRedirectUri(getString(R.string.SPOTIFY_REDIRECT_URI))
            .showAuthView(true)
            .build()
    }

    private var code: String? = null
    private val clientSecret = "f507dc67396d4469aebcac1e91a5b867"
    private var refreshToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()
    }

    override fun onStop() {
        super.onStop()
        mSpotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    private fun setListener() {
       /* auth_button.setOnClickListener {
            requestAuth()
        }

        access_button.setOnClickListener {
            getAccessCode()
        }

        refresh_button.setOnClickListener {
            refreshAccessCode()
        }*/
    }
/*
    private fun requestAuth() {
        val REQUEST_CODE = 1337

        val builder = AuthenticationRequest.Builder(
            getString(R.string.SPOTIFY_CLIENT_ID),
            AuthenticationResponse.Type.CODE,
            getString(R.string.SPOTIFY_REDIRECT_URI)
        )

        builder.setScopes(arrayOf("streaming", "user-read-private"))
        val request = builder.build()

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    Log.d(TAG, "AuthenticationResponse.Type.TOKEN : ${response.accessToken}")
                    Log.d(TAG, "AuthenticationResponse.Type.TOKEN : ${response.expiresIn}")
                    Log.d(TAG, "AuthenticationResponse.Type.TOKEN : ${response.type}")
//                    search(response.accessToken)
                }

                AuthenticationResponse.Type.CODE -> {
                    Log.d(TAG, "AuthenticationResponse.Type.CODE : ${response.accessToken}")
                    Log.d(TAG, "AuthenticationResponse.Type.CODE : ${response.expiresIn}")
                    Log.d(TAG, "AuthenticationResponse.Type.CODE : ${response.code}")
                    code = response.code
                }
                AuthenticationResponse.Type.ERROR -> {
                    Log.d(TAG, "AuthenticationResponse.Type.ERROR : ${response.error}")
                    response.error
                }
                else -> {
                    Log.d(TAG, "AuthenticationResponse.Type.else")
                }
            }
        }
    }

    private fun search(token: String) {
        val api = SpotifyApi()
        api.setAccessToken(token)
        val spotify = api.service
        spotify.searchTracks("meditation", object : Callback<TracksPager> {
            override fun success(t: TracksPager?, response: Response?) {
                t?.tracks?.items?.forEach {
                    Log.d(TAG, "${it.id}")
                }
            }

            override fun failure(error: RetrofitError?) {
            }
        })

        spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", object : Callback<Album?> {
            override fun success(album: Album?, response: Response?) {
                Log.d(TAG, "${album?.name}")
            }

            override fun failure(error: RetrofitError) {
                Log.d(TAG, error.toString())
            }
        })


    }

    private fun getAccessCode() {
        val credentials = SpotifyCredentials(
            client_id = getString(R.string.SPOTIFY_CLIENT_ID),
            client_secret = clientSecret,
            code = code,
            redirect_uri = getString(R.string.SPOTIFY_REDIRECT_URI)
        )
        SpotifyCalls.getAccessToken(credentials,
            {
                Log.d(
                    TAG,
                    "Response : ${it.access_token}\n${it.token_type}\n${it.expires_in}\n${it.refresh_token}\n${it.scope}"
                )
                refreshToken = it.refresh_token
            }, {
                Log.e(TAG, "Error code : $it")
            }
        )
    }

    private fun refreshAccessCode() {
        val credentials = SpotifyCredentials(
            client_id = getString(R.string.SPOTIFY_CLIENT_ID),
            client_secret = clientSecret,
            refresh_token = refreshToken
        )
        SpotifyCalls.getRefreshToken(credentials,
            {
                Log.d(
                    TAG,
                    "Response : ${it.access_token}\n${it.token_type}\n${it.expires_in}\n${it.refresh_token}\n${it.scope}"
                )
            }, {
                Log.e(TAG, "Error code : $it")
            }
        )
    }*/
}