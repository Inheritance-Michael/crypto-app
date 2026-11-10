package com.example.cryptoapp.presentation.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.presentation.coin_detail.component.CoinTag
import com.example.cryptoapp.presentation.coin_detail.component.TeamListItem


@Composable
fun CoinDetailScreen(
    uiState: CoinDetailState,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()){
        uiState.coin?.let {
            coin ->
            LazyColumn(
                modifier= Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier= modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text= "${coin.rank}\t${coin.name}\t(${coin.symbol})",
                            style = MaterialTheme.typography.titleLarge,
                            modifier= Modifier.weight(8f)
                        )
                        Text(
                            text = if(coin.isActive) "active" else "inactive",
                            color = if(coin.isActive) Color.Green else Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(2f)
                        )
                    }
                    Spacer(modifier= Modifier.height(15.dp))
                    Text(
                        text = coin.description,
                        style = MaterialTheme.typography.bodyMedium

                    )
                    Spacer(modifier= Modifier.height(15.dp))
                    Text(
                        text = "Tags",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ){
                        coin.tags.forEach { tag->
                            CoinTag(
                                tag = tag
                            )
                        }
                    }
                    Spacer(modifier= Modifier.height(15.dp))
                    Text(
                        text = "Team Members",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier= Modifier.height(15.dp))
                }
                items(coin.team){
                    teamMember ->
                    TeamListItem(
                        teamMembers= teamMember,
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Divider()
                }
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

        if(uiState.loading){
            CircularProgressIndicator(modifier= Modifier.align(Alignment.Center) )
        }
    }
}