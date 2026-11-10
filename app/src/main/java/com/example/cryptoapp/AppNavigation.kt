package com.example.cryptoapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.cryptoapp.presentation.Screen
import com.example.cryptoapp.presentation.coin_detail.CoinDetailScreen
import com.example.cryptoapp.presentation.coin_detail.CoinDetailViewModel
import com.example.cryptoapp.presentation.coin_list.CoinListScreen
import com.example.cryptoapp.presentation.coin_list.CoinListViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Screen.CoinListScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Screen.CoinListScreen> {
                val viewModel: CoinListViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value

                CoinListScreen(
                    uiState = state,
                    onCoinClick = { id ->
                        backStack.add(Screen.CoinDetailScreen(id))
                    }
                )
            }

            entry<Screen.CoinDetailScreen> { key ->
                val viewModel: CoinDetailViewModel = hiltViewModel()
                LaunchedEffect(key.id) {
                    viewModel.loadCoin(key.id)
                }
                val state = viewModel.state.collectAsState().value
                CoinDetailScreen(
                    uiState = state
                )
            }
        }
    )
}
