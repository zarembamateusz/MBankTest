package com.shmz.feature_currency_list

import com.shmz.core_model.model.CurrencyRate
import com.shmz.domain.FetchCurrencyRateListUC
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListVMTest {

    private val fetchCurrencyRateListUC: FetchCurrencyRateListUC = mockk()
    private val ioDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CurrencyListVM

    @Before
    fun setUp() {
        Dispatchers.setMain(ioDispatcher)
        viewModel = CurrencyListVM(
            fetchCurrencyRateListUC = fetchCurrencyRateListUC,
            ioDispatcher = ioDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onStart should emit Loading and then Data on success`() = runTest {
        val mockList = listOf(
            CurrencyRate("Dollar", "USD", 4.2),
            CurrencyRate("Euro", "EUR", 4.5)
        )

        coEvery { fetchCurrencyRateListUC() } returns flowOf(
            FetchCurrencyRateListUC.FetchCurrencyRateListUCState(isLoading = true),
            FetchCurrencyRateListUC.FetchCurrencyRateListUCState(currencyRateList = mockList)
        )

        viewModel.onStart()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ICurrencyListVM.CurrencyListScreenState.Data)
        val dataState = state as ICurrencyListVM.CurrencyListScreenState.Data
        assertEquals(mockList, dataState.currencyRateList)
    }

    @Test
    fun `onStart should emit Error when UnexpectedError occurs`() = runTest {
        val errorMessage = "Unexpected Error"
        coEvery { fetchCurrencyRateListUC() } returns flowOf(
            FetchCurrencyRateListUC.FetchCurrencyRateListUCState(errorMessage = errorMessage)
        )

        viewModel.onStart()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state is ICurrencyListVM.CurrencyListScreenState.Error)
        state as ICurrencyListVM.CurrencyListScreenState.Error
        assertEquals(errorMessage, state.errorMessage)
    }
}
