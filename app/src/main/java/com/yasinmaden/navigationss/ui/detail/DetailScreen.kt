package com.yasinmaden.navigationss.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
    productId: Int
) {
    val viewModel: DetailViewModel = hiltViewModel()


    LaunchedEffect(productId) {
        viewModel.loadProductById(productId)
    }
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
        )
    }
}

@Composable
fun DetailContent(
    navController: NavHostController,
    uiState: DetailContract.UiState,
    onAction: (DetailContract.UiAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = uiState.product.thumbnail,
                contentDescription = null,
            )
            Text(text = uiState.product.title)
            Text(text = uiState.product.description)
            Text(text = uiState.product.price.toString())
            Text(text = uiState.product.category)
            Text(text = uiState.product.rating.toString())
        }


    }

}