package com.shmz.core_api.services

import com.shmz.core_api.response_model.ArrayOfExchangeRatesTable
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("/api/exchangerates/tables/A")
    suspend fun getExchangeRates(): Response<ArrayOfExchangeRatesTable>
}