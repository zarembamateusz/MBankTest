package com.shmz.core_data.converter

import com.shmz.core_api.response_model.Rate
import com.shmz.core_model.model.CurrencyRate

fun Rate.asDomainModel(): CurrencyRate =
    CurrencyRate(
        currencyName = currency ?: "" ,
        code = code ?: "",
        mid = mid
    )