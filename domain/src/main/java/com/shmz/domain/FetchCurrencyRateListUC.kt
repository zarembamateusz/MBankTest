package com.shmz.domain

import com.shmz.core_data.repositories.CurrencyRepository
import com.shmz.core_model.model.CurrencyRate
import com.shmz.core_model.result.CurrencyListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class FetchCurrencyRateListUC @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) : IFetchCurrencyRateListUC {
    override operator fun invoke(): Flow<IFetchCurrencyRateListUCState> = flow {
        emit(FetchCurrencyRateListUCState(isLoading = true))
        try {
            val result = currencyRepository.fetchCurrencyList().last()
            when (result) {
                CurrencyListResult.NetworkError ->
                    emit(FetchCurrencyRateListUCState(errorMessage = "NetworkError"))

                is CurrencyListResult.Success ->
                    emit(FetchCurrencyRateListUCState(currencyRateList = result.currencyRateList))

                CurrencyListResult.UnexpectedError ->
                    emit(FetchCurrencyRateListUCState(errorMessage = "UnexpectedError"))
            }
        } catch (e: Exception) {
            emit(FetchCurrencyRateListUCState(errorMessage = e.message ?: "Unexpected error"))
        }
    }



    data class FetchCurrencyRateListUCState(
        override val isLoading: Boolean = false,
        override val errorMessage: String? = null,
        override val currencyRateList: List<CurrencyRate> = emptyList()
    ) : IFetchCurrencyRateListUCState
}

interface IFetchCurrencyRateListUC {
    operator fun invoke(): Flow<IFetchCurrencyRateListUCState>
}

interface IFetchCurrencyRateListUCState {
    val isLoading: Boolean
    val errorMessage: String?
    val currencyRateList: List<CurrencyRate>
}