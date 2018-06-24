package br.com.riccimac.fincontrol.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.dao.TransactionDAO
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import br.com.riccimac.fincontrol.ui.dialog.AddTransactionDialog
import br.com.riccimac.fincontrol.ui.dialog.UpdateTransactionDialog
import br.com.riccimac.fincontrol.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*

class TransactionListActivity : AppCompatActivity() {

    private val dao = TransactionDAO()
    private val transactions = dao.transactions
    private val REMOVE_MENU_POSITION = 1

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
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, REMOVE_MENU_POSITION, Menu.NONE, "Remover")
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
                .call(type, delegate =
                    { transactionAdded ->
                        add(transactionAdded)
                        list_transaction_add_menu.close(true)
                    }
                )

    }

    private fun callDialogToUpdate(position: Int) {
        val transaction = transactions[position]
        UpdateTransactionDialog(this, viewGroupActivity)
                .call(transaction, delegate =
                    { transactionUpdated ->
                        update(transactionUpdated, position)
                    }
                )
    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val menuPosition = item?.itemId
        if(menuPosition == REMOVE_MENU_POSITION){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val transactionPosition = adapterMenuInfo.position
            remove(transactionPosition)
        }

        return super.onContextItemSelected(item)
    }

    private fun add(transaction: Transaction) {
        dao.add(transaction)
        updateTransactionList()
    }

    private fun update(transaction: Transaction, position: Int) {
        dao.update(transaction, position)
        updateTransactionList()
    }

    private fun remove(position: Int) {
        dao.remove(position)
        updateTransactionList()
    }

    private fun updateTransactionList() {
        setupListViewAdapter()
        setupSummaryView()
    }
}
