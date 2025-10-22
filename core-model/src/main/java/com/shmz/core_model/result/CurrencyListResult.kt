package com.shmz.core_model.result

import com.shmz.core_model.model.CurrencyRate

sealed interface CurrencyListResult {
    data class Success(
        val currencyRateList: List<CurrencyRate>
    ) : CurrencyListResult

    data object NetworkError : CurrencyListResult {
        override fun toString(): String {
            return "Network error"
        }
    }

    data object UnexpectedError : CurrencyListResult {
        override fun toString(): String {
            return "Unexpected error"
        }
    }
}