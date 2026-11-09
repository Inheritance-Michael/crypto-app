package com.example.cryptoapp.data.repositoryimpl

import com.example.cryptoapp.data.remote.CoinPaprikaApi
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val coinPaprikaApi: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getCoin(): List<CoinDto> {
        return coinPaprikaApi.getCoins()
    }

    override suspend fun getCoinById(id: String): CoinDetailDto {
       return coinPaprikaApi.getCoinById(id)
    }
}