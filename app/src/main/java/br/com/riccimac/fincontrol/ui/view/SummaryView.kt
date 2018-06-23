package br.com.riccimac.fincontrol.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.extensions.formatToBrazillianCurrency
import br.com.riccimac.fincontrol.model.Summary
import br.com.riccimac.fincontrol.model.Transaction
import kotlinx.android.synthetic.main.summary_card.view.*
import java.math.BigDecimal

class SummaryView(private val context: Context,
                  private val view: View,
                  transactions: List<Transaction>) {

    private val summary : Summary = Summary(transactions)
    private val colorIncome : Int = ContextCompat.getColor(context, R.color.income)
    private val colorOutcome : Int = ContextCompat.getColor(context, R.color.outcome)

    fun update() {
        addIncome()
        addOutcome()
        addTotal()
    }

    private fun addIncome() {
        val totalIncome = summary.income

        with(view.summary_card_income){
            text = totalIncome.formatToBrazillianCurrency()
            setTextColor(colorIncome)
        }
    }

    private fun addOutcome() {
        val totalOutcome = summary.outcome

        with(view.summary_card_outcome){
            text = totalOutcome.formatToBrazillianCurrency()
            setTextColor(colorOutcome)
        }
    }

    private fun addTotal(){
        val total = summary.total
        val color: Int = colorBy(total)

        with(view.summary_card_total){
            text = total.formatToBrazillianCurrency()
            setTextColor(color)
        }

        view.summary_card_total.text = total.formatToBrazillianCurrency()
    }

    private fun colorBy(value: BigDecimal): Int {
        if (value >= BigDecimal.ZERO) {
           return ContextCompat.getColor(context, R.color.income)
        }
        return ContextCompat.getColor(context, R.color.outcome)
    }
}