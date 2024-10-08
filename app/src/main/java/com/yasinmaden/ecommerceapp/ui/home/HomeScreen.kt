package com.yasinmaden.ecommerceapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.yasinmaden.ecommerceapp.R
import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails
import com.yasinmaden.ecommerceapp.navigation.DetailsScreen
import com.yasinmaden.ecommerceapp.ui.components.EmptyScreen
import com.yasinmaden.ecommerceapp.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    uiState: HomeContract.UiState,
    uiEffect: Flow<HomeContract.UiEffect>,
    onAction: (HomeContract.UiAction) -> Unit,
    navController: NavHostController,
) {


    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is HomeContract.UiEffect.NavigateTo -> {
                    navController.navigate(effect.route)
                }

                is HomeContract.UiEffect.NavigateToProductDetails -> {
                    val itemId = effect.product.id
                    navController.navigate("${DetailsScreen.Information.route}/$itemId")
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> HomeContent(
            uiState = uiState,
            onAction = onAction
        )
    }
}

@Composable
fun HomeContent(
    uiState: HomeContract.UiState,
    onAction: (HomeContract.UiAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart,

        ) {
        Column {
            WelcomeSection()
            SearchBar()
            ChooseCategorySection(
                uiState = uiState,
                onAction = onAction
            )
            ProductSection(
                uiState = uiState,
                onAction = onAction
            )
        }
    }
}

@Composable
fun ChooseCategorySection(
    uiState: HomeContract.UiState,
    onAction: (HomeContract.UiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Choose Category",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.categories) { brand ->
                CategoryCard(
                    categoryName = brand,
                    onAction = onAction
                )
            }
        }
    }
}


@Composable
fun CategoryCard(
    categoryName: String,
    onAction: (HomeContract.UiAction) -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .clickable { onAction(HomeContract.UiAction.OnCategorySelected(categoryName)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProductCard(
    product: ProductDetails,
    onAction: (HomeContract.UiAction) -> Unit
) {
    Box(
        modifier = Modifier
            .size(160.dp, 265.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onAction(HomeContract.UiAction.OnProductSelected(product)) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1, // Limit to 1 line if necessary
                overflow = TextOverflow.Ellipsis, // Ellipsis if text is too long
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            // Price text below the title
            Text(
                text = product.price.toString(),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
        IconButton(
            onClick = { onAction(HomeContract.UiAction.OnFavoriteClicked(product)) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                if (product.isFavorite) {
                    ImageVector.vectorResource(R.drawable.favorites_filled)
                } else {
                    ImageVector.vectorResource(R.drawable.favorites)
                },
                contentDescription = "Favorite"
            )
        }
    }
}


@Composable
fun ProductSection(
    uiState: HomeContract.UiState,
    onAction: (HomeContract.UiAction) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "New Arrival",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        if (uiState.isLoadingProducts) {
            LoadingBar()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {

                items(uiState.products) { product ->
                    ProductCard(
                        product = product,
                        onAction = onAction
                    )
                }
            }
        }

    }
}


@Composable
fun WelcomeSection() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Hello", style = MaterialTheme.typography.headlineLarge)
        Text("Welcome to laza.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp, top = 0.dp, start = 16.dp, end = 16.dp),
        placeholder = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = "Search"
                )
                Spacer(Modifier.padding(8.dp))
                Text("Search...")
            }

        }
    )
}


@Composable
@Preview(showBackground = true)
fun PreviewApp() {
    val sampleCategories = listOf("Electronics", "Clothing", "Books", "Toys")

    HomeScreen(
        uiState = HomeContract.UiState(
            categories = sampleCategories
        ),
        uiEffect = flowOf(),
        onAction = {},
        navController = NavHostController(LocalContext.current),
    )
}