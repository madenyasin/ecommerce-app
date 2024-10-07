package com.yasinmaden.ecommerceapp.repository

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) {
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    fun signOut() {
        googleSignInClient.signOut()
    }
}