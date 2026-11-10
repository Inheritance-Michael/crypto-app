package com.example.cryptoapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.usescase.getcoins.GetCoins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoins: GetCoins
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state: StateFlow<CoinListState> = _state.asStateFlow()

    init {
        getCoins()
    }

    private fun getCoins(){
        getCoins.invoke().onEach { result ->
            when(result){
                is Resource.Error<*> -> errorHandler(result.message)
                is Resource.Loading<*> -> loadCoinHandler()
                is Resource.Success<*> -> successHandler(result.data)
            }
        }.launchIn(viewModelScope)
    }

    private fun successHandler(data: List<Coin>?) {
        _state.update {
            it.copy(
                coins = data ?: emptyList()
            )
        }
    }

    private fun loadCoinHandler() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun errorHandler(message: String?) {
       _state.update {
           it.copy(
               error = message ?: "An unexpected error occurred"
           )
       }
    }
}


