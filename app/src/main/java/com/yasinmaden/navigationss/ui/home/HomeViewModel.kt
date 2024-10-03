package com.yasinmaden.navigationss.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.repository.CategoryRepository
import com.yasinmaden.navigationss.repository.FirebaseDatabaseRepository
import com.yasinmaden.navigationss.repository.ProductRepository
import com.yasinmaden.navigationss.ui.components.BottomBarScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseDatabaseRepository: FirebaseDatabaseRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.UiState())
    val uiState: StateFlow<HomeContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<HomeContract.UiEffect>() }
    val uiEffect: Flow<HomeContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: HomeContract.UiAction) {
        when (uiAction) {
            is HomeContract.UiAction.OnTabSelected -> updateSelectedTab(uiAction.screen)
            is HomeContract.UiAction.OnCategorySelected -> viewModelScope.launch {
                loadProductsByCategory(uiAction.category)
            }

            is HomeContract.UiAction.OnProductSelected -> viewModelScope.launch {
                emitUiEffect(HomeContract.UiEffect.NavigateToProductDetails(uiAction.product))
                loadProductDetails(uiAction.product.id)
            }

            is HomeContract.UiAction.OnFavoriteClicked -> onFavoriteClicked(uiAction.product)
        }
    }


    init {
        viewModelScope.launch {
            try {
                loadProducts()
                loadCategories()
            } catch (e: Exception) {
                Log.e("DataLoad", "Veriler yüklenirken hata oluştu: ${e.message}")
            }
        }
    }


    private fun onFavoriteClicked(product: ProductDetails) {
        val updatedProduct = product.copy(isFavorite = !product.isFavorite)
        _uiState.update { currentState ->
            currentState.copy(
                products = currentState.products.map { productItem ->
                    if (productItem.id == updatedProduct.id) {
                        updatedProduct
                    } else {
                        productItem
                    }
                }
            )
        }
        if (updatedProduct.isFavorite){
            firebaseAuth.currentUser?.let {
                firebaseDatabaseRepository.addFavoriteItem(
                    user = it,
                    product = updatedProduct
                )
            }
        } else{
            firebaseAuth.currentUser?.let {
                firebaseDatabaseRepository.removeFavoriteItem(
                    user = it,
                    product = updatedProduct
                )
            }
        }

    }


    private suspend fun loadProductsByCategory(categoryName: String): Resource<ProductResponse> {
        _uiState.update { it.copy(isLoadingProducts = true) }
        when (val request = productRepository.getProductsByCategory(categoryName)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        products = request.data.products,
                        isLoadingProducts = false
                    )
                }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                _uiState.update { it.copy(isLoadingProducts = false) }
                return Resource.Error(exception = request.exception)
            }
        }
    }

    private suspend fun loadProductDetails(id: Int): Resource<ProductDetails> {
        _uiState.update { it.copy(isLoading = true) }
        when (val request = productRepository.getProductById(id)) {
            is Resource.Success -> {
                _uiState.update { it.copy(product = request.data, isLoading = false) }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                return Resource.Error(exception = request.exception)
            }
        }
    }

    private suspend fun loadProducts(): Resource<ProductResponse> {
        _uiState.update { it.copy(isLoading = true) }
        when (val request = productRepository.getProducts()) {
            is Resource.Success -> {
                _uiState.update { it.copy(products = request.data.products, isLoading = false) }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                return Resource.Error(exception = request.exception)
            }
        }
    }

    private suspend fun loadCategories(): Resource<List<String>> {
        _uiState.update { it.copy(isLoading = true) }
        when (val request = categoryRepository.getCategories()) {
            is Resource.Success -> {
                _uiState.update { it.copy(categories = request.data, isLoading = false) }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                _uiState.update { it.copy(isLoading = false) }
                return Resource.Error(exception = request.exception)
            }
        }
    }

    private fun updateSelectedTab(screen: BottomBarScreen) = viewModelScope.launch {
        _uiState.update { it.copy(currentTab = screen) }
        emitUiEffect(HomeContract.UiEffect.NavigateTo(screen.route))
    }


    private fun updateUiState(block: HomeContract.UiState.() -> HomeContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: HomeContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}