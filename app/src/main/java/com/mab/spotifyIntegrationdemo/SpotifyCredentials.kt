package com.mab.spotifyIntegrationdemo

data class SpotifyCredentials(
    var client_id: String? = null,
    var client_secret: String? = null,
    var code: String? = null,
    var access_token: String? = null,
    var token_type: String? = null,
    var expires_in: Long? = null,
    var createdAt: Long? = null,
    var refresh_token: String? = null,
    var scope: String? = null,
    var redirect_uri: String?=null
)