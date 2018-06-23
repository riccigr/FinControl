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

    fun addIncome() {
        val totalIncome = summary.income()

        view.summary_card_income.text = totalIncome.formatToBrazillianCurrency()
        view.summary_card_income.setTextColor(ContextCompat.getColor(context, R.color.income))
    }

    fun addOutcome() {
        val totalOutcome = summary.outcome()

        view.summary_card_outcome.text = totalOutcome.formatToBrazillianCurrency()
        view.summary_card_outcome.setTextColor(ContextCompat.getColor(context, R.color.outcome))

    }

    fun addTotal(){
        val total = summary.total()

        if(total.compareTo(BigDecimal.ZERO) >= 0){
            view.summary_card_total.setTextColor(ContextCompat.getColor(context, R.color.income))
        }else{
            view.summary_card_total.setTextColor(ContextCompat.getColor(context, R.color.outcome))
        }

        view.summary_card_total.text = total.formatToBrazillianCurrency()
    }
}