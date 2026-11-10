package com.example.cryptoapp.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.usescase.getcoin.GetCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoin: GetCoin,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(CoinDetailState())
    val state: StateFlow<CoinDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAMETER_COIN_ID)?.let {
            getCoin(it)
        }
    }

    fun loadCoin(id: String) {
        if (_state.value.coin?.id == id && !_state.value.loading) return
        getCoin(id)
    }

    private fun getCoin(id: String) {
        getCoin.invoke(id).onEach { result ->
            when(result) {
                is Resource.Error<*> -> errorHandler(result.message)
                is Resource.Loading<*> -> loadCoinHandler()
                is Resource.Success<*> -> successHandler(result.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun successHandler(data: CoinDetail?) {
        _state.update {
            it.copy(
                coin = data,
                loading = false,
                error = ""
            )
        }
    }

    private fun loadCoinHandler() {
        _state.update {
            it.copy(
                loading = true
            )
        }
    }

    private fun errorHandler(message: String?) {
       _state.update {
           it.copy(
               error = message ?: "An unexpected error occurred",
               loading = false
           )
       }
    }
}
