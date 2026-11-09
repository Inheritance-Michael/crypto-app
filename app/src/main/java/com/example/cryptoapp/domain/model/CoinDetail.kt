package com.example.cryptoapp.domain.model

import com.example.cryptoapp.data.remote.dto.Tag
import com.example.cryptoapp.data.remote.dto.TeamMembers

data class CoinDetail(
    val id: String,
    val name: String,
    val description: String,
    val rank: Int,
    val symbol: String,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMembers>
)
