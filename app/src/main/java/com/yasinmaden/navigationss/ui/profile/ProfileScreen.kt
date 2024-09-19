package com.yasinmaden.navigationss.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.di.FirebaseModule.provideFirebaseAuth
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import com.yasinmaden.navigationss.ui.navigation.Graph
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiAction
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiEffect
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiState
import com.yasinmaden.navigationss.utils.GoogleSignInManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ProfileScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
    navController: NavHostController,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> ProfileContent(
            navController = navController
        )
    }
}

@Composable
fun ProfileContent(
    firebaseAuth: FirebaseAuth = provideFirebaseAuth(),
    navController: NavHostController,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            firebaseAuth.currentUser?.let { user ->
                Text(
                    text = "Welcome, ${user.displayName}!",
                    fontSize = 20.sp,
                )
                Button(
                    onClick = {
                        provideFirebaseAuth().signOut()
                        // googleSignInClient.signOut()
                        GoogleSignInManager(
                            context = navController.context
                        ).signOut()
                        // Navigate to the Authentication screen and clear the back stack
                        navController.navigate(Graph.AUTHENTICATION) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive =
                                    true // Removes everything from the back stack, including the current destination
                            }

                        }
                    }
                ) {
                    Text(text = "Sign Out", fontSize = 20.sp)
                }
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