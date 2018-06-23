package br.com.riccimac.fincontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import br.com.riccimac.fincontrol.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*
import java.math.BigDecimal

class TransactionListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        val transactions = mockTransactions()

        setupSummaryView(transactions)
        setupList(transactions)
    }

    private fun setupSummaryView(transactions: List<Transaction>) {
        val view = window.decorView
        val summaryView = SummaryView( this, view, transactions)

        summaryView.addIncome()
        summaryView.addOutcome()
        summaryView.addTotal()
    }

    private fun mockTransactions(): List<Transaction> {
        return listOf(
                Transaction(value = BigDecimal(20.5), category = "Almoço com a equipe", type = Type.OUTCOME),
                Transaction(value = BigDecimal(1101), type = Type.INCOME),
                Transaction(value = BigDecimal(400), category = "Passagem", type = Type.OUTCOME),
                Transaction(value = BigDecimal(50), category = "Presente", type = Type.INCOME)
        )
    }

    private fun setupList(transactions: List<Transaction>) {
        list_transaction_listview.adapter = TransactionListAdapter(transactions, this)
    }
}
