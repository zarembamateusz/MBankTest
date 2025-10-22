package com.shmz.core_data.repositories

import com.shmz.core_api.CurrencyApi
import com.shmz.core_api.CurrencyApiResult
import com.shmz.core_data.converter.asDomainModel
import com.shmz.core_model.result.CurrencyListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
) : ICurrencyRepository {
    override fun fetchCurrencyList(): Flow<CurrencyListResult> = flow {
        when (val currencyApiResult = api.getExchangeRates()) {
            CurrencyApiResult.NetworkError -> emit(CurrencyListResult.NetworkError)
            is CurrencyApiResult.Success ->
                emit(
                    CurrencyListResult.Success(
                        currencyRateList = currencyApiResult.currencyList.map { currency ->
                            currency.asDomainModel()

                        }
                    )
                )

            CurrencyApiResult.UnexpectedError -> emit(CurrencyListResult.UnexpectedError)
        }
    }

}

interface ICurrencyRepository {
    fun fetchCurrencyList(): Flow<CurrencyListResult>
}