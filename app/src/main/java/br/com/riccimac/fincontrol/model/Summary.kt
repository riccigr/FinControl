package br.com.riccimac.fincontrol.model

import java.math.BigDecimal

class Summary(private val transactions: List<Transaction>) {

    val income get() = sumBy(Type.INCOME)

    val outcome get() = sumBy(Type.OUTCOME)

    val total: BigDecimal get() = income.subtract(outcome)

    private fun sumBy(type: Type): BigDecimal {
        val sum = transactions
                .filter { it.type == type }
                .sumByDouble { it.value.toDouble() }

        return BigDecimal(sum)
    }
}