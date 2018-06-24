package br.com.riccimac.fincontrol.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.riccimac.fincontrol.R
import br.com.riccimac.fincontrol.model.Type

class AddTransactionDialog(context: Context,
                           viewGroup: ViewGroup) : FormTransactionDialog(context, viewGroup) {

    override val positiveButtonLabel: String
        get() = "Adicionar"

    override fun titleBy(type: Type): Int {
        if (type == Type.INCOME) {
            return R.string.add_income
        }
        return  R.string.add_outcome
    }


}
