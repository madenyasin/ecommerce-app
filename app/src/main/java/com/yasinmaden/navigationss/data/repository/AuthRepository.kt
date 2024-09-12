package com.yasinmaden.navigationss.data.repository

import android.content.Intent
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yasinmaden.navigationss.R
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.utils.GoogleSignInManager
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInManager: GoogleSignInManager
) {
    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signInWithGoogle(idToken: String): Resource<String> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
//            Resource.Success(result.user?.uid.orEmpty())
            Resource.Success("Google sign-in successful!")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
    fun getSignInIntent(): Intent {
        return googleSignInManager.getSignInIntent()
    }

    fun handleSignInResult(data: Intent?): Task<GoogleSignInAccount> {
        return googleSignInManager.handleSignInResult(data)
    }

}