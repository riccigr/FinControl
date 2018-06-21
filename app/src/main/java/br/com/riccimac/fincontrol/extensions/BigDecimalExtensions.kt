package br.com.riccimac.fincontrol.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatToBrazillianCurrency() : String {
    val brazillianFormat = DecimalFormat.getCurrencyInstance(Locale("pt","br"))
    val realCurrency = brazillianFormat.format(this).replace("R$", "R$ ")

    return realCurrency
}