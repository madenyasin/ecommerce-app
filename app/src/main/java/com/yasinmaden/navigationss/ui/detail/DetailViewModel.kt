package com.yasinmaden.navigationss.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import com.yasinmaden.navigationss.repository.ProductRepository
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
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailContract.UiState())
    val uiState: StateFlow<DetailContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<DetailContract.UiEffect>() }
    val uiEffect: Flow<DetailContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }



    fun loadProductById(id: Int) {
        viewModelScope.launch {
            loadProductDetails(id)
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


    fun onAction(uiAction: DetailContract.UiAction) {

    }

    private fun updateUiState(block: DetailContract.UiState.() -> DetailContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: DetailContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}