package com.example.cryptoapp.domain.usescase.getcoin

import android.util.Log.e
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.data.remote.mapper.toCoin
import com.example.cryptoapp.data.remote.mapper.toCoinDetail
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoin @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(id: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = coinRepository.getCoinById(id).toCoinDetail()
            emit(Resource.Success(coin))
        } catch (e: HttpException){
            emit(Resource.Error(e.message?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}