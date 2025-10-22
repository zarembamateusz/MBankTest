package com.shmz.feature_currency_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shmz.core_model.model.CurrencyRate
import com.shmz.core_ui.components.ErrorScreen
import com.shmz.core_ui.components.LoadingScreen
import com.shmz.core_utils.StartEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.shmz.core_utils.asString


@Composable
fun CurrencyListScreen(
    viewModel: CurrencyListVM = hiltViewModel()
) {
    StartEffect {
        viewModel.onStart()
    }
    val screenState = viewModel.uiState.collectAsStateWithLifecycle()
    CurrencyListScreen(
        screenState = screenState.value,
        onCurrencyClick = {
            // Will be implemented after add currency details screen
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    screenState: ICurrencyListVM.CurrencyListScreenState,
    onCurrencyClick: (CurrencyRate) -> Unit,
) {
    when (screenState) {
        ICurrencyListVM.CurrencyListScreenState.Loading -> LoadingScreen()
        is ICurrencyListVM.CurrencyListScreenState.Error -> ErrorScreen(
            errorMessage = screenState.errorMessage,
            onRetry = screenState.onRetry
        )

        is ICurrencyListVM.CurrencyListScreenState.Data -> {
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(R.string.currency_list_title.asString()) })
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val currencyRateList = screenState.currencyRateList
                    if (currencyRateList.isEmpty()) {
                        Text(R.string.no_data.asString(), textAlign = TextAlign.Center)
                    } else {

                        LazyColumn {
                            items(currencyRateList) { currencyRate ->
                                CurrencyListItem(rate = currencyRate, onClick = onCurrencyClick)
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyListItem(
    rate: CurrencyRate,
    onClick: (CurrencyRate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(rate) }
            .padding(16.dp)
    ) {
        Text(text = rate.currencyName, style = MaterialTheme.typography.h5)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = rate.code, style = MaterialTheme.typography.body1)
            Text(
                text = R.string.currency_format.asString(rate.mid ?: 0.0),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
