package br.com.riccimac.fincontrol.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.model.Transaction
import kotlinx.android.synthetic.main.transacao_item.view.*
import java.text.SimpleDateFormat

class TransactionListAdapter(transactions: List<Transaction>,
                             context: Context) : BaseAdapter() {

    private val transactions = transactions
    private val context = context

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewItem = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transaction = transactions[position]
        val formatedDate = SimpleDateFormat("dd/MM/yyyy").format(transaction.date.time)

        viewItem.transacao_valor.text = transaction.value.toString()
        viewItem.transacao_categoria.text = transaction.category
        viewItem.transacao_data.text = formatedDate

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