package br.com.riccimac.fincontrol.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.extensions.formatToBrazillianStandard
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import br.com.riccimac.fincontrol.ui.adapter.TransactionListAdapter
import br.com.riccimac.fincontrol.ui.view.SummaryView
import kotlinx.android.synthetic.main.activity_transaction_list.*
import kotlinx.android.synthetic.main.form_transaction.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class TransactionListActivity : AppCompatActivity() {

    private val transactions : MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        setupSummaryView()
        setupList()

        list_transaction_add_income.setOnClickListener {
            val decorView = window.decorView
            val alertView = LayoutInflater.from(this)
                            .inflate(R.layout.form_transaction,
                                    decorView as ViewGroup,
                                    false)

            val year = 2018
            val month = 4
            val day = 23

            val today = Calendar.getInstance()
            alertView.form_transaction_date.setText(today.formatToBrazillianStandard())

            alertView.form_transaction_date.setOnClickListener{
                DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { _, year, month, day ->
                            val dateSelected = Calendar.getInstance()
                            dateSelected.set(year,month,day)
                            alertView.form_transaction_date.setText(dateSelected.formatToBrazillianStandard())
                        },
                        year, month, day
                ).show()
            }

            val adapterCategory = ArrayAdapter.createFromResource(this,
                                                            R.array.category_income,
                                                            android.R.layout.simple_spinner_dropdown_item)

            alertView.form_transaction_category.adapter = adapterCategory

            AlertDialog.Builder(this)
                    .setTitle(R.string.add_income)
                    .setView(alertView)
                    .setPositiveButton("Adicionar",
                            DialogInterface.OnClickListener { dialogInterface, i ->
                                val textValue = alertView.form_transaction_value.text.toString()
                                val textDate = alertView.form_transaction_date.text.toString()
                                val textCategory = alertView.form_transaction_category.selectedItem.toString()

                                val value =
                                    try {
                                            BigDecimal(textValue)
                                    }catch (ex : NumberFormatException){
                                        Toast.makeText(this,
                                                        "Falha na Conversão de valores",
                                                        Toast.LENGTH_LONG).show()
                                        BigDecimal.ZERO
                                    }

                                val brazillianFormat = SimpleDateFormat("dd/MM/yyyy")
                                val date = Calendar.getInstance()
                                date.time = brazillianFormat.parse(textDate)

                                val transaction = Transaction(type = Type.INCOME,
                                        value = value,
                                        createDate = date,
                                        category = textCategory)

                                updateTransactionList(transaction)
                                list_transaction_add_menu.close(true)

                            }
                    )
                    .setNegativeButton("Cancelar", null)
                    .show()
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
