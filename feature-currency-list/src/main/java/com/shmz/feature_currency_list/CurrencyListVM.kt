package com.shmz.feature_currency_list

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shmz.core_model.model.CurrencyRate
import com.shmz.domain.FetchCurrencyRateListUC
import com.shmz.feature_currency_list.ICurrencyListVM.CurrencyListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListVM @Inject constructor(
    private val fetchCurrencyRateListUC: FetchCurrencyRateListUC,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), ICurrencyListVM {

    private val _uiState: MutableStateFlow<CurrencyListScreenState> = MutableStateFlow(
        CurrencyListScreenState.Loading
    )

    override val uiState: StateFlow<CurrencyListScreenState> = _uiState

    fun onStart() {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(ioDispatcher) {
            fetchCurrencyRateListUC()
                .collect { state ->
                    if(state.isLoading) {
                        _uiState.value = CurrencyListScreenState.Loading
                    } else if (state.errorMessage != null) {
                        _uiState.value = CurrencyListScreenState.Error(
                            errorMessage = state.errorMessage ?: "",
                            onRetry = ::fetchData
                        )
                    } else {
                        _uiState.value = CurrencyListScreenState.Data(
                            currencyRateList = state.currencyRateList
                        )
                    }
                }
        }
    }
}

interface ICurrencyListVM {
    val uiState: StateFlow<CurrencyListScreenState>

    @Stable
    sealed class CurrencyListScreenState {
        data class Data(
            val currencyRateList: List<CurrencyRate>
        ) : CurrencyListScreenState()

        data class Error(
            val errorMessage: String,
            val onRetry: () -> Unit
        ) : CurrencyListScreenState()

        data object Loading : CurrencyListScreenState()
    }
}