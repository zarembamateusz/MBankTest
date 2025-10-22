package com.shmz.core_api.response_model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ArrayOfExchangeRatesTable", strict = false)
data class ArrayOfExchangeRatesTable(
    @field:Element(name = "ExchangeRatesTable")
    var exchangeRatesTable: ExchangeRatesTable? = null
)

@Root(name = "ExchangeRatesTable", strict = false)
data class ExchangeRatesTable(
    @field:Element(name = "Table")
    var table: String? = null,

    @field:Element(name = "No")
    var no: String? = null,

    @field:Element(name = "EffectiveDate")
    var effectiveDate: String? = null,

    @field:Element(name = "Rates", required = false)
    var rates: Rates? = null
)

@Root(name = "Rates", strict = false)
data class Rates(
    @field:ElementList(inline = true, entry = "Rate", required = false)
    var rateList: List<Rate>? = null
)

@Root(name = "Rate", strict = false)
data class Rate(
    @field:Element(name = "Currency")
    var currency: String? = null,

    @field:Element(name = "Code")
    var code: String? = null,

    @field:Element(name = "Mid")
    var mid: Double? = null
)