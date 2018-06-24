package br.com.riccimac.fincontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.delegate.ITransactionDelegate
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import br.com.riccimac.fincontrol.ui.dialog.AddTransactionDialog
import br.com.riccimac.fincontrol.ui.dialog.UpdateTransactionDialog
import br.com.riccimac.fincontrol.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*

class TransactionListActivity : AppCompatActivity() {

    private val transactions: MutableList<Transaction> = mutableListOf()
    private val viewActivity by lazy {
        window.decorView
    }

    private val viewGroupActivity by lazy {
        viewActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        setupSummaryView()
        setupListViewAdapter()
        setupFloatingMenu()

    }

    private fun setupSummaryView() {
        val summaryView = SummaryView(this, viewActivity, transactions)

        summaryView.update()
    }

    private fun setupListViewAdapter() {
        val adapterTransaction = TransactionListAdapter(transactions, this)

        with(list_transaction_listview){
            adapter = adapterTransaction
            setOnItemClickListener { _, _, position, _ ->
                callDialogToUpdate(position)
            }
        }

    }

    private fun setupFloatingMenu() {
        list_transaction_add_income.setOnClickListener {
            callDialogToAdd(Type.INCOME)
        }

        list_transaction_add_outcome.setOnClickListener {
            callDialogToAdd(Type.OUTCOME)
        }
    }


    private fun callDialogToAdd(type: Type) {
        AddTransactionDialog(this, viewGroupActivity)
                .call(type, object : ITransactionDelegate {
                    override fun delegate(transaction: Transaction) {

                        add(transaction)
                        list_transaction_add_menu.close(true)

                    }
                })
    }

    private fun add(transaction: Transaction) {
        transactions.add(transaction)
        updateTransactionList()
    }

    private fun callDialogToUpdate(position: Int) {
        val transaction = transactions[position]
        UpdateTransactionDialog(this, viewGroupActivity)
                .call(transaction, object : ITransactionDelegate {
                    override fun delegate(transaction: Transaction) {
                        update(transaction, position)
                    }

                })
    }

    private fun update(transaction: Transaction, position: Int) {
        transactions[position] = transaction
        updateTransactionList()
    }

    private fun updateTransactionList() {
        setupListViewAdapter()
        setupSummaryView()
    }
}
