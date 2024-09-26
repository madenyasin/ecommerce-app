package com.yasinmaden.navigationss.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.domain.repository.CategoryRepository
import com.yasinmaden.navigationss.domain.repository.ProductRepository
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
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeContract.UiState())
    val uiState: StateFlow<HomeContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<HomeContract.UiEffect>() }
    val uiEffect: Flow<HomeContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: HomeContract.UiAction) {
        when (uiAction) {
            is HomeContract.UiAction.OnTabSelected -> updateSelectedTab(uiAction.screen)
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

    private suspend fun loadProducts(): Resource<ProductResponse> {
        Log.d("loadProducts", "Veri çekme işlemi başladı") // Başlangıç logu
        _uiState.update { it.copy(isLoading = true) }
        when (val request = productRepository.getProducts()) {
            is Resource.Success -> {
                Log.d("loadProducts", "Veri başarıyla çekildi. Ürünler listeleniyor...")

                // Her bir ürünü alt alta logluyoruz
                request.data.products.forEach { product ->
                    Log.d("loadProducts", "Ürün: $product")
                }
                _uiState.update { it.copy(products = request.data.products, isLoading = false) }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                Log.e(
                    "loadProducts",
                    "Veri çekme hatası: ${request.exception.message}"
                ) // Hata durumunda log
                _uiState.update { it.copy(isLoading = false) }
                return Resource.Error(exception = request.exception)
            }
        }
    }

    private suspend fun loadCategories(): Resource<List<String>> {
        Log.d("loadCategories", "Veri çekme işlemi başladı")
        _uiState.update { it.copy(isLoading = true) }
        when (val request = categoryRepository.getCategories()) {
            is Resource.Success -> {
                Log.d("loadCategories", "Veri başarıyla çekildi. Kategoriler listeleniyor...")

                request.data.forEach{ category->
                    Log.d("loadCategories", "Kategori: $category")
                }

                _uiState.update { it.copy(categories = request.data, isLoading = false) }
                return Resource.Success(data = request.data)
            }

            is Resource.Error -> {
                Log.e("loadCategories", "Veri çekme hatası: ${request.exception.message}")
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