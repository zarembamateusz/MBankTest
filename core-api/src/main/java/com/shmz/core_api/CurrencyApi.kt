package com.shmz.core_api

import com.shmz.core_api.response_model.Rate
import com.shmz.core_api.services.CurrencyApiService
import javax.inject.Inject

interface ICurrencyApi {
    suspend fun getExchangeRates(): CurrencyApiResult
}

class CurrencyApi @Inject constructor(
    private val currencyApiService: CurrencyApiService
) : ICurrencyApi {

    override suspend fun getExchangeRates(): CurrencyApiResult {
        val response = currencyApiService.getExchangeRates()
        return if (response.isSuccessful) {
            response.body()?.let {
                CurrencyApiResult.Success(
                    currencyList = it.exchangeRatesTable?.rates?.rateList ?: emptyList()
                )
            } ?: CurrencyApiResult.UnexpectedError
        } else {
            CurrencyApiResult.NetworkError
        }
    }
}

sealed interface CurrencyApiResult {
    data class Success(
        val currencyList: List<Rate>
    ) : CurrencyApiResult

    data object NetworkError : CurrencyApiResult

    data object UnexpectedError : CurrencyApiResult
}