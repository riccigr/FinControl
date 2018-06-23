package br.com.riccimac.fincontrol.ui

import android.view.View
import br.com.riccimac.fincontrol.extensions.formatToBrazillianCurrency
import br.com.riccimac.fincontrol.model.Summary
import br.com.riccimac.fincontrol.model.Transaction
import kotlinx.android.synthetic.main.summary_card.view.*

class SummaryView(private val view: View,
                  transactions: List<Transaction>) {

    private val summary : Summary = Summary(transactions)

    fun addIncome() {
        val totalIncome = summary.income()

        view.summary_card_income.text = totalIncome.formatToBrazillianCurrency()
    }

    fun addOutcome() {
        val totalOutcome = summary.outcome()

        view.summary_card_outcome.text = totalOutcome.formatToBrazillianCurrency()
    }

    fun addTotal(){
        val total = summary.total()

        view.summary_card_total.text = total.formatToBrazillianCurrency()
    }
}