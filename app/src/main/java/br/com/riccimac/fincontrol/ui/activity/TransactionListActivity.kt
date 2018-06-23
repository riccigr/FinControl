package br.com.riccimac.fincontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.delegate.ITransactionDelegate
import br.com.riccimac.fincontrol.dialog.AddTransactionDialog
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import br.com.riccimac.fincontrol.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*

class TransactionListActivity : AppCompatActivity() {

    private val transactions : MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        setupSummaryView()
        setupList()

        this.list_transaction_add_income.setOnClickListener {
            AddTransactionDialog(this, window.decorView as ViewGroup)
                    .configureDialog(object : ITransactionDelegate{
                        override fun delegate(transaction: Transaction) {
                            updateTransactionList(transaction)
                            list_transaction_add_menu.close(true)
                        }
                    })
        }

    }

    private fun updateTransactionList(transaction: Transaction) {
        transactions.add(transaction)
        setupList()
        setupSummaryView()
    }

    private fun setupSummaryView() {
        val view = window.decorView
        val summaryView = SummaryView( this, view, transactions)

        summaryView.update()
    }

    private fun setupList() {
        list_transaction_listview.adapter = TransactionListAdapter(transactions, this)
    }
}
