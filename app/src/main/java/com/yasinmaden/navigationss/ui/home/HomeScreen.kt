package com.yasinmaden.navigationss.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
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
import androidx.wear.compose.material.OutlinedButton
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            WelcomeSection()
            SearchBar()
        }


    }
}

@Composable
fun ChooseBrandSection() {

}

@Composable
fun BrandCard(brandName: String) {
    Card {
        OutlinedButton(
            onClick = { /* Handle button click */ },
            modifier = Modifier.size(115.dp, 50.dp),
        ) {
            Text(brandName)
        }
    }
}

@Composable
@Preview
fun BrandCardPreview() {
    BrandCard("Adidas")
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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        placeholder = {
            Row(verticalAlignment = Alignment.CenterVertically){
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
    HomeScreen(
        uiState = HomeContract.UiState(),
        uiEffect = flowOf(),
        onAction = {},
        navController = NavHostController(LocalContext.current)

    )
}