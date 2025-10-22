package com.shmz.core_data

import com.shmz.core_api.CurrencyApi
import com.shmz.core_api.CurrencyApiResult
import com.shmz.core_api.response_model.Rate
import com.shmz.core_data.repositories.CurrencyRepository
import com.shmz.core_model.result.CurrencyListResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CurrencyRepositoryTest {

    private val api: CurrencyApi = mockk()
    private lateinit var repository: CurrencyRepository

    @Before
    fun setUp() {
        repository = CurrencyRepository(api)
    }

    @Test
    fun `fetchCurrencyList emits Success when API returns Success`() = runTest {
        val rates = listOf(
            Rate(currency = "Dollar", code = "USD", mid = 4.2),
            Rate(currency = "Euro", code = "EUR", mid = 4.5)
        )
        coEvery { api.getExchangeRates() } returns CurrencyApiResult.Success(rates)

        val result = repository.fetchCurrencyList().first()

        assert(result is CurrencyListResult.Success)
        val success = result as CurrencyListResult.Success
        assert(success.currencyRateList.size == 2)
        assert(success.currencyRateList[0].currencyName == "Dollar")
        assert(success.currencyRateList[0].code == "USD")
        assert(success.currencyRateList[0].mid == 4.2)
    }

    @Test
    fun `fetchCurrencyList emits NetworkError when API returns NetworkError`() = runTest {
        coEvery { api.getExchangeRates() } returns CurrencyApiResult.NetworkError

        val result = repository.fetchCurrencyList().first()

        assert(result is CurrencyListResult.NetworkError)
    }

    @Test
    fun `fetchCurrencyList emits UnexpectedError when API returns UnexpectedError`() = runTest {
        coEvery { api.getExchangeRates() } returns CurrencyApiResult.UnexpectedError

        val result = repository.fetchCurrencyList().first()

        assert(result is CurrencyListResult.UnexpectedError)
    }
}
