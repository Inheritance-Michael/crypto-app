package com.example.cryptoapp.data.remote.mapper

import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.model.CoinDetail

fun CoinDto.toCoin(): Coin{
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}

fun CoinDetailDto.toCoinDetail(): CoinDetail{
    return CoinDetail(
        id = id,
        name = name,
        description = description,
        rank = rank,
        symbol = symbol,
        isActive = isActive,
        tags = tags.map { it.name },
        team = team
    )
}