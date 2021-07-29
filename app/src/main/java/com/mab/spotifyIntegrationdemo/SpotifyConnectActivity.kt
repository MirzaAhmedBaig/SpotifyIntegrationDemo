package com.mab.spotifyIntegrationdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp
import com.spotify.android.appremote.api.error.NotLoggedInException
import com.spotify.android.appremote.api.error.UserNotAuthorizedException
import com.spotify.protocol.client.Subscription
import com.spotify.protocol.types.PlayerState
import kotlinx.android.synthetic.main.activity_spotify_connect.*
import kotlinx.coroutines.launch


class SpotifyConnectActivity : AppCompatActivity() {

    private val TAG = SpotifyConnectActivity::class.java.simpleName
    private val REQUEST_CODE = 1337

    private var mSpotifyAppRemote: SpotifyAppRemote? = null
    private val connectionParams by lazy {
        ConnectionParams.Builder(getString(R.string.SPOTIFY_CLIENT_ID))
            .setRedirectUri(getString(R.string.SPOTIFY_REDIRECT_URI))
            .showAuthView(true)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_connect)
        Log.d(TAG, "Is Installed ${SpotifyAppRemote.isSpotifyInstalled(this)}")
        SpotifyAppRemote.setDebugMode(true)
        bvConnect.setOnClickListener {
            connect()
        }

//        requestAuth()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSpotifyAppRemote?.let {
            mSpotifyAppRemote?.playerApi?.pause()
            SpotifyAppRemote.disconnect(it)
        }
    }

    private fun setListener() {
        bvPlay.setOnClickListener {
            playDemoTrack()
        }
    }

    private fun connect() {

        lifecycleScope.launch {
            SpotifyAppRemote.connect(
                application,
                connectionParams,
                object : Connector.ConnectionListener {
                    override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote
                        Log.d(TAG, "Connected! Yay!")
                        // Now you can start interacting with App Remote
                        connected()
                    }

                    override fun onFailure(throwable: Throwable) {
                        if (throwable is NotLoggedInException || throwable is UserNotAuthorizedException) {
                            Log.e(TAG, "Not logged in : ${throwable.message}")
                        } else if (throwable is CouldNotFindSpotifyApp) {
                            Log.e(TAG, "Not downloaded : ${throwable.message}")
                        }

                        Log.e(TAG, throwable.message, throwable)
                    }
                })
        }


    }

    private fun connected() {
        subscribeToPlayer()
        setListener()
    }

    private fun subscribeToPlayer() {
        mSpotifyAppRemote?.playerApi?.subscribeToPlayerState()
            ?.setEventCallback(playerStateEventCallback)
            ?.setLifecycleCallback(
                object : Subscription.LifecycleCallback {
                    override fun onStart() {
                        Log.d(TAG, "Event: start")
                    }

                    override fun onStop() {
                        Log.d(TAG, "Event: end")
                    }
                })
            ?.setErrorCallback {
                Log.e(TAG, "Error : $it")
            } as Subscription<PlayerState>

    }

    private val playerStateEventCallback = Subscription.EventCallback<PlayerState> { playerState ->
        Log.v(TAG, String.format("Player State: %s", Gson().toJson(playerState)))
        Log.d(TAG, "${playerState.playbackPosition}")
    }

    private fun playDemoTrack() {
        mSpotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult : ${requestCode}")
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
    }*/
}