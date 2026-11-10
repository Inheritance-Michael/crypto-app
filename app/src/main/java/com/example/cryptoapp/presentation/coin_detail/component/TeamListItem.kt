package com.example.cryptoapp.presentation.coin_detail.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.data.remote.dto.TeamMembers

@Composable
fun TeamListItem(
    teamMembers: TeamMembers,
    modifier: Modifier = Modifier
) {
    Text(
        text = teamMembers.name,
        style = MaterialTheme.typography.headlineMedium
    )
    Spacer(modifier=Modifier.height(4.dp))
    Text(
        text = teamMembers.position,
        style = MaterialTheme.typography.bodySmall,
        fontStyle = FontStyle.Italic
    )
}