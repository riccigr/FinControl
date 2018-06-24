package br.com.riccimac.fincontrol.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.delegate.ITransactionDelegate
import br.com.riccimac.fincontrol.extensions.formatToBrazillianStandard
import br.com.riccimac.fincontrol.model.Transaction
import br.com.riccimac.fincontrol.model.Type

class UpdateTransactionDialog(private val context: Context,
                              viewGroup: ViewGroup) : FormTransactionDialog(context, viewGroup){
    override val positiveButtonLabel: String
        get() = "Alterar"

    fun call(transaction: Transaction, transactionDelegate: ITransactionDelegate) {
        val type = transaction.type

        super.call(type, transactionDelegate)

        initFields(transaction)
    }

    private fun initFields(transaction: Transaction) {
        initValueField(transaction)
        initCreateDateField(transaction)
        initCategoryField(transaction)
    }

    private fun initValueField(transaction: Transaction) {
        valueField.setText(transaction.value.toString())
    }

    private fun initCreateDateField(transaction: Transaction) {
        createDateField.setText(transaction.createDate.formatToBrazillianStandard())
    }

    private fun initCategoryField(transaction: Transaction) {
        val categoriesList = context.resources.getStringArray(categoryBy(transaction.type))
        val position = categoriesList.indexOf(transaction.category)
        categoryField.setSelection(position, true)
    }

    override fun titleBy(type: Type): Int {
        if (type == Type.INCOME) {
            return R.string.update_income
        }
        return  R.string.update_outcome
    }

}
