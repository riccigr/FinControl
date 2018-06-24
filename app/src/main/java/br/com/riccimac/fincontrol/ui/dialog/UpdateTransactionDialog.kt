package br.com.riccimac.fincontrol.ui.dialog

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
import java.util.*

class UpdateTransactionDialog(private val context: Context,
                              private val viewGroup: ViewGroup) {

    private val view = createLayout()

    private val valueField = view.form_transaction_value
    private val categoryField = view.form_transaction_category
    private val createDateField = view.form_transaction_date

    fun call(transaction: Transaction, ITransactionDelegate: ITransactionDelegate) {
        val type = transaction.type

        configureCreateDate()
        configureCategory(type)
        configureForm(type, ITransactionDelegate)

        valueField.setText(transaction.value.toString())
        createDateField.setText(transaction.createDate.formatToBrazillianStandard())

        val categoriesList = context.resources.getStringArray(categoryBy(transaction.type))
        val position = categoriesList.indexOf(transaction.category)
        categoryField.setSelection(position,true)
    }

    private fun configureCreateDate() {
        val today = Calendar.getInstance()

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        createDateField.setText(today.formatToBrazillianStandard())

        createDateField.setOnClickListener {
            DatePickerDialog(context,
                    { _, year, month, day ->
                        val dateSelected = Calendar.getInstance()
                        dateSelected.set(year, month, day)
                        createDateField.setText(dateSelected.formatToBrazillianStandard())
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
                .setPositiveButton("Atualizar") { _ , _ ->
                    val textValue = valueField.text.toString()
                    val textDate = createDateField.text.toString()
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
            return R.string.update_income
        }
        return  R.string.update_outcome
    }

    private fun categoryBy(type: Type): Int {
        if (type == Type.INCOME) {
            return R.array.category_income
        }
        return R.array.category_outcome
    }
}