package com.yasinmaden.navigationss.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yasinmaden.navigationss.R
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    uiState: HomeContract.UiState,
    uiEffect: Flow<HomeContract.UiEffect>,
    onAction: (HomeContract.UiAction) -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is HomeContract.UiEffect.NavigateTo -> {
                    navController.navigate(effect.route)
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> HomeContent(
            navController = navController,
            uiState = uiState
        )
    }
}

@Composable
fun HomeContent(
    navController: NavHostController,
    uiState: HomeContract.UiState
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
            ChooseCategorySection(uiState.categories)
        }
    }
}

@Composable
fun ChooseCategorySection(categories: List<String>) {
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
            items(categories) { brand ->
                CategoryCard(categoryName = brand)
            }
        }
    }
}


@Composable
fun CategoryCard(categoryName: String) {
    Card(
        modifier = Modifier
            .height(50.dp),
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
        navController = NavHostController(LocalContext.current)

    )
}