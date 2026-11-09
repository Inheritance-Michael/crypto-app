package com.example.cryptoapp.domain.repository

import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoin(): List<CoinDto>

    suspend fun getCoinById(id: String): CoinDetailDto
}