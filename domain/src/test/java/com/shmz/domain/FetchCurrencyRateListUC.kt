package com.shmz.domain

import com.shmz.core_data.repositories.CurrencyRepository
import com.shmz.core_model.model.CurrencyRate
import com.shmz.core_model.result.CurrencyListResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchCurrencyRateListUCTest {

    private val repository: CurrencyRepository = mockk()
    private lateinit var useCase: FetchCurrencyRateListUC

    @Before
    fun setUp() {
        useCase = FetchCurrencyRateListUC(repository)
    }

    @Test
    fun `invoke should emit Loading first and then Data on Success`() = runTest {
        val mockRates = listOf(
            CurrencyRate("Dollar", "USD", 4.2),
            CurrencyRate("Euro", "EUR", 4.5)
        )
        coEvery { repository.fetchCurrencyList() } returns flowOf(
            CurrencyListResult.Success(mockRates)
        )

        val states = useCase().toList()

        assert(states.first().isLoading)
        assert(states[1].currencyRateList == mockRates)
        assert(states[1].errorMessage == null)
    }

    @Test
    fun `invoke should emit Error on NetworkError`() = runTest {
        coEvery { repository.fetchCurrencyList() } returns flowOf(CurrencyListResult.NetworkError)

        val states = mutableListOf<IFetchCurrencyRateListUCState>()
        val job = launch {
            useCase().collect { states.add(it) }
        }
        advanceUntilIdle()
        job.cancel()

        assert(states.size >= 2)
        assert(states[0].isLoading)
        val errorState = states[1]
        assert(errorState.errorMessage?.contains("NetworkError") == true)
        assert(errorState.currencyRateList.isEmpty())
    }


    @Test
    fun `invoke should emit Error on UnexpectedError`() = runTest {
        coEvery { repository.fetchCurrencyList() } returns flowOf(CurrencyListResult.UnexpectedError)

        val states = useCase().toList()

        assert(states.first().isLoading)
        val errorState = states[1]
        assert(errorState.errorMessage?.contains("UnexpectedError") == true)
        assert(errorState.currencyRateList.isEmpty())
    }

    @Test
    fun `invoke should emit Error when exception occurs`() = runTest {
        val exceptionMessage = "Some exception"
        coEvery { repository.fetchCurrencyList() } returns flow {
            throw RuntimeException(exceptionMessage)
        }

        val states = useCase().toList()

        assert(states.first().isLoading)
        val errorState = states[1]
        assert(errorState.errorMessage == exceptionMessage)
        assert(errorState.currencyRateList.isEmpty())
    }
}
