package br.com.riccimac.fincontrol.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.extensions.formatToBrazillianCurrency
import br.com.riccimac.fincontrol.extensions.formatToBrazillianStandard
import br.com.riccimac.fincontrol.extensions.limitUntil
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionListAdapter(private val transactions: List<Transaction>,
                             private val context: Context) : BaseAdapter() {

    private val LABEL_LENGTH = 14

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewItem = LayoutInflater.from(context).
                                                    inflate(R.layout.transaction_item,
                                                            parent,
                                                            false)

        val transaction = transactions[position]

        addValue(viewItem, transaction)
        addCategory(viewItem, transaction)
        addCreateDate(viewItem, transaction)
        addIcon(transaction, viewItem)

        return viewItem
    }

    private fun addValue(viewItem: View, transaction: Transaction) {
        val color : Int = colorBy(transaction.type)
        viewItem.transaction_value.setTextColor(color)
        viewItem.transaction_value.text = transaction.value.formatToBrazillianCurrency()
    }

    private fun addCategory(viewItem: View, transaction: Transaction) {
        viewItem.transaction_category.text = transaction.category.limitUntil(LABEL_LENGTH)
    }

    private fun addCreateDate(viewItem: View, transaction: Transaction) {
        viewItem.transaction_date.text = transaction.createDate.formatToBrazillianStandard()
    }

    private fun addIcon(transaction: Transaction, viewItem: View) {
        val icon : Int = iconBy(transaction)
        viewItem.transaction_icon.setBackgroundResource(icon)
    }

    private fun colorBy(type: Type): Int {
        if (type == Type.OUTCOME) {
           return ContextCompat.getColor(context, R.color.outcome)
        }

        return ContextCompat.getColor(context, R.color.income)
    }

    private fun iconBy(transaction: Transaction): Int {
        if (transaction.type == Type.OUTCOME) {
            return R.drawable.icon_transaction_outcome
        }

        return R.drawable.icon_transaction_income
    }

    override fun getItem(position: Int): Transaction {
        return transactions[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transactions.size
    }
}