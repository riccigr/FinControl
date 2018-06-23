package br.com.riccimac.fincontrol.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.delegate.ITransactionDelegate
import br.com.riccimac.fincontrol.extensions.formatToBrazillianStandard
import br.com.riccimac.fincontrol.extensions.parseToCalendar
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type
import kotlinx.android.synthetic.main.form_transaction.view.*
import java.math.BigDecimal
import java.util.*

class AddTransactionDialog(private val context: Context,
                           private val viewGroup: ViewGroup) {

    private val alertView = createLayout()

    fun configureDialog(ITransactionDelegate: ITransactionDelegate) {
        configureCreateDate()
        configureCategory()
        configureForm(ITransactionDelegate)
    }

    private fun configureForm(ITransactionDelegate: ITransactionDelegate) {
        AlertDialog.Builder(context)
                .setTitle(R.string.add_income)
                .setView(alertView)
                .setPositiveButton("Adicionar",
                        DialogInterface.OnClickListener { _ , _ ->
                            val textValue = alertView.form_transaction_value.text.toString()
                            val textDate = alertView.form_transaction_date.text.toString()
                            val textCategory = alertView.form_transaction_category.selectedItem.toString()

                            val value = convertToBigDecimal(textValue)
                            val date = textDate.parseToCalendar()

                            val transaction = Transaction(type = Type.INCOME,
                                    value = value,
                                    createDate = date,
                                    category = textCategory)

                            ITransactionDelegate.delegate(transaction)

                        }
                )
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configureCategory() {
        val adapterCategory = ArrayAdapter.createFromResource(
                context,
                R.array.category_income,
                android.R.layout.simple_spinner_dropdown_item)

        alertView.form_transaction_category.adapter = adapterCategory
    }

    private fun configureCreateDate() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        alertView.form_transaction_date.setText(today.formatToBrazillianStandard())

        alertView.form_transaction_date.setOnClickListener {
            DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { _, year, month, day ->
                        val dateSelected = Calendar.getInstance()
                        dateSelected.set(year, month, day)
                        alertView.form_transaction_date.setText(dateSelected.formatToBrazillianStandard())
                    },
                    year, month, day
            ).show()
        }
    }

    private fun createLayout(): View {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.form_transaction,
                         viewGroup,
                        false)
        return view
    }

    private fun convertToBigDecimal(textValue: String): BigDecimal {
        return try {
            BigDecimal(textValue)
        } catch (ex: NumberFormatException) {
            Toast.makeText(context,
                    "Falha na Conversão de valores",
                    Toast.LENGTH_LONG
            ).show()
            BigDecimal.ZERO
        }
    }
}
