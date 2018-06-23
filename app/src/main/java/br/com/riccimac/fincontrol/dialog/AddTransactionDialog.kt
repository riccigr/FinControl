package br.com.riccimac.fincontrol.dialog

import android.app.DatePickerDialog
import android.content.Context
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
import java.util.Calendar

class AddTransactionDialog(private val context: Context,
                           private val viewGroup: ViewGroup) {

    private val view = createLayout()

    private val dateField = view.form_transaction_date
    private val categoryField = view.form_transaction_category
    private val valueField = view.form_transaction_value

    fun configure(type: Type, ITransactionDelegate: ITransactionDelegate) {
        configureCreateDate()
        configureCategory(type)
        configureForm(type, ITransactionDelegate)
    }

    private fun configureCreateDate() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        dateField.setText(today.formatToBrazillianStandard())

        dateField.setOnClickListener {
            DatePickerDialog(context,
                    { _, year, month, day ->
                        val dateSelected = Calendar.getInstance()
                        dateSelected.set(year, month, day)
                        dateField.setText(dateSelected.formatToBrazillianStandard())
                    },
                    year, month, day
            ).show()
        }
    }

    private fun configureCategory(type: Type) {

        val categoryList = categoryBy(type)

        val adapterCategory = ArrayAdapter.createFromResource(
                context,
                categoryList,
                android.R.layout.simple_spinner_dropdown_item)

        categoryField.adapter = adapterCategory
    }

    private fun configureForm(type: Type, ITransactionDelegate: ITransactionDelegate) {

        val title = titleBy(type)

        AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("Adicionar") { _ , _ ->
                    val textValue = valueField.text.toString()
                    val textDate = dateField.text.toString()
                    val textCategory = categoryField.selectedItem.toString()

                    val value = convertToBigDecimal(textValue)
                    val date = textDate.parseToCalendar()

                    val transaction = Transaction(type = type,
                            value = value,
                            createDate = date,
                            category = textCategory)

                    ITransactionDelegate.delegate(transaction)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun createLayout(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.form_transaction,viewGroup,false)
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

    private fun titleBy(type: Type): Int {
        if (type == Type.INCOME) {
            return R.string.add_income
        }
        return  R.string.add_outcome
    }

    private fun categoryBy(type: Type): Int {
        if (type == Type.INCOME) {
            return R.array.category_income
        }
        return R.array.category_outcome
    }
}
