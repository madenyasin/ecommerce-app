package com.yasinmaden.navigationss.ui.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow

@Composable
fun WishlistScreen(
    uiState: WishlistContract.UiState,
    uiEffect: Flow<WishlistContract.UiEffect>,
    onAction: (WishlistContract.UiAction) -> Unit,
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        uiEffect.collect {
            TODO()
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> WishlistContent(
            navController = navController,
            uiState = uiState,
            onAction = onAction,
        )
    }
}

@Composable
fun WishlistContent(
    navController: NavHostController,
    uiState: WishlistContract.UiState,
    onAction: (WishlistContract.UiAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.wishlist) { product ->
            WishlistItem(product = product)
        }
    }
}


@Composable
fun WishlistItem(product: ProductDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: ${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${product.stock}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

        }
    }
}