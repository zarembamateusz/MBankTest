package com.shmz.feature_currency_list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shmz.core_model.model.CurrencyRate

@Preview(showBackground = true, name = "Currency List - Data")
@Composable
fun CurrencyListScreenPreview() {
    val mockData = listOf(
        CurrencyRate("Dollar", "USD", 4.2145),
        CurrencyRate("Euro", "EUR", 4.5678),
        CurrencyRate("Funt szterling", "GBP", 5.1234),
        CurrencyRate("Frank szwajcarski", "CHF", 4.3210)
    )

    CurrencyListScreen(
        screenState = ICurrencyListVM.CurrencyListScreenState.Data(
            currencyRateList = mockData
        ),
        onCurrencyClick = { /* Mock click */ }
    )
}

@Preview(showBackground = true, name = "Currency List - Loading")
@Composable
fun CurrencyListScreenLoadingPreview() {
    CurrencyListScreen(
        screenState = ICurrencyListVM.CurrencyListScreenState.Loading,
        onCurrencyClick = {}
    )
}

@Preview(showBackground = true, name = "Currency List - Error")
@Composable
fun CurrencyListScreenErrorPreview() {
    CurrencyListScreen(
        screenState = ICurrencyListVM.CurrencyListScreenState.Error(
            errorMessage = "Nie udało się pobrać danych",
            onRetry = {}
        ),
        onCurrencyClick = {}
    )
}
