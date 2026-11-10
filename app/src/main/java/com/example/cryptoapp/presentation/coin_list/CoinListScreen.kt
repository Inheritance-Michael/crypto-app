package com.example.cryptoapp.presentation.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.presentation.coin_list.component.CoinListItem


@Suppress("ParamsComparedByRef")
@Composable
fun CoinListScreen(
    uiState: CoinListState,
    onCoinClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
   Box(modifier = Modifier.fillMaxSize()){
       LazyColumn(modifier= Modifier.fillMaxSize()) {
           items(uiState.coins){
               coin ->
               CoinListItem(
                   coin = coin,
                   onItemClick = { onCoinClick(it.id) }
               )
           }
       }
       if(uiState.error.isNotBlank()){
           Text(
               text = uiState.error,
               color = MaterialTheme.colorScheme.error,
               textAlign = TextAlign.Center,
               modifier = Modifier
                   .padding(horizontal = 20.dp)
                   .align(Alignment.Center)
           )
       }

       if(uiState.isLoading){
           CircularProgressIndicator(modifier= Modifier.align(Alignment.Center) )
       }
   }
}