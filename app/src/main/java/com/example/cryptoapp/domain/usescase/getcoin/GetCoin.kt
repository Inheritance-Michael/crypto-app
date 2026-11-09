package com.example.cryptoapp.domain.usescase.getcoin


import android.net.http.HttpException
import com.example.cryptoapp.common.Resource
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoin @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<CoinDto>>> = flow {
       try {
            emit(Resource.Loading())
           val coins = r
       } catch (e: HttpException){

       }catch (e: IOException){

       }
    }
}