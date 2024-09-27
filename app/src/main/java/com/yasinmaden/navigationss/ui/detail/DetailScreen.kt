package com.yasinmaden.navigationss.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow


@Composable
fun DetailScreen(
    uiState: DetailContract.UiState,
    uiEffect: Flow<DetailContract.UiEffect>,
    onAction: (DetailContract.UiAction) -> Unit,
    navController: NavHostController,
    modifier: Modifier,
    itemId: String
) {
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            TODO()
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> DetailContent(
            navController = navController,
            uiState = uiState,
            onAction = onAction,
            itemId = itemId
        )
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    uiState: DetailContract.UiState,
    onAction: (DetailContract.UiAction) -> Unit,
    itemId: String
) {
    Text(text = "Detail Screen $itemId")

}