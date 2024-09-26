package com.yasinmaden.navigationss.ui.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.di.FirebaseModule.provideFirebaseAuth
import com.yasinmaden.navigationss.di.GoogleModule.provideGoogleSignInClient
import com.yasinmaden.navigationss.domain.repository.GoogleAuthRepository
import com.yasinmaden.navigationss.navigation.Graph
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiAction
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiEffect
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiState
import com.yasinmaden.navigationss.ui.theme.Gray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ProfileScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navController: NavHostController,
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is UiEffect.ShowToast -> {
                     Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProfileContent(
            navController = navController,
            firebaseAuth = viewModel.firebaseAuth,
            googleAuthRepository = viewModel.googleAuthRepository
        )
    }
}

@Composable
fun ProfileContent(
    navController: NavHostController,
    firebaseAuth: FirebaseAuth,
    googleAuthRepository: GoogleAuthRepository
) {
    val user = firebaseAuth.currentUser

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            user?.let {
                // Display profile picture if available
                it.photoUrl?.let { photoUri ->
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Display user name
                Text(
                    text = it.displayName ?: "No Name",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Display email
                Text(
                    text = "Email: ${it.email ?: "No Email"}",
                    fontSize = 16.sp,
                    color = Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sign-out button
                Button(
                    onClick = {
                        try {
                            firebaseAuth.signOut()
                            googleAuthRepository.signOut()
                            navController.navigate(Graph.AUTHENTICATION) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true // Clear back stack
                                }
                            }
                        } catch (e: Exception) {
                            UiEffect.ShowToast(e.message.toString())
                        }

                    }
                ) {
                    Text(text = "Sign Out", fontSize = 20.sp)
                }
            } ?: run {
                Text(text = "No user is logged in.", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(
    @PreviewParameter(ProfileScreenPreviewProvider::class) uiState: UiState,
) {
    ProfileScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navController = NavHostController(LocalContext.current)
    )
}