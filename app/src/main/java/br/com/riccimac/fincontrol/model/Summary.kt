package br.com.riccimac.fincontrol.model

import java.math.BigDecimal

class Summary(private val transactions: List<Transaction>) {

    fun income(): BigDecimal {
        var totalIncome = BigDecimal.ZERO

        for (transaction in transactions) {
            if (transaction.type == Type.INCOME) {
                totalIncome = totalIncome.plus(transaction.value)
            }
        }

        return totalIncome
    }

    fun outcome() : BigDecimal {
        var totalOutcome = BigDecimal.ZERO

        for (transaction in transactions) {
            if (transaction.type == Type.OUTCOME) {
                totalOutcome = totalOutcome.plus(transaction.value)
            }
        }

        return totalOutcome
    }

    fun total() : BigDecimal{
        return income().subtract(outcome())
    }
}