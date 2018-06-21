package br.com.riccimac.fincontrol.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.extensions.formatToBrazillianCurrency
import br.com.riccimac.fincontrol.extensions.formatToBrazillianStandard
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import kotlinx.android.synthetic.main.transaction_item.view.*
import java.text.SimpleDateFormat

class TransactionListAdapter(transactions: List<Transaction>,
                             context: Context) : BaseAdapter() {

    private val transactions = transactions
    private val context = context

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewItem = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)

        val transaction = transactions[position]

        viewItem.transaction_value.text = transaction.value.formatToBrazillianCurrency()
        viewItem.transaction_category.text = transaction.category
        viewItem.transaction_date.text = transaction.date.formatToBrazillianStandard()

        if(transaction.type == Type.OUTCOME){
            viewItem.transaction_value.setTextColor(ContextCompat.getColor(context, R.color.outcome))
            viewItem.transaction_icon.setBackgroundResource(R.drawable.icon_transaction_outcome)
        }else{
            viewItem.transaction_value.setTextColor(ContextCompat.getColor(context, R.color.income))
            viewItem.transaction_icon.setBackgroundResource(R.drawable.icon_transaction_income)
        }



        return viewItem
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