package com.example.cryptoapp.presentation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen: NavKey {
    @Serializable
    data object CoinListScreen: Screen
    @Serializable
    data class CoinDetailScreen(val id: String): Screen
}